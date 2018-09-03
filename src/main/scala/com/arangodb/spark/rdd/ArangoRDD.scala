/*
 * DISCLAIMER
 *
 * Copyright 2016 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 * 
 * author Mark - mark at arangodb.com
 */

package com.arangodb.spark.rdd

import scala.collection.JavaConverters.asScalaIteratorConverter
import scala.collection.JavaConverters.mapAsJavaMapConverter
import scala.reflect.ClassTag

import org.apache.spark.Partition
import org.apache.spark.SparkContext
import org.apache.spark.TaskContext
import org.apache.spark.rdd.RDD

import com.arangodb.ArangoCursor
import com.arangodb.ArangoDB
import com.arangodb.spark.ReadOptions
import com.arangodb.spark.createArangoBuilder
import com.arangodb.spark.rdd.api.java.ArangoJavaRDD
import com.arangodb.spark.rdd.partition.ArangoPartition

class ArangoRDD[T: ClassTag](
    @transient override val sparkContext: SparkContext,
    val options: ReadOptions,
    val conditions: List[String] = List()) extends RDD[T](sparkContext, Nil) {

  override def compute(split: Partition, context: TaskContext): Iterator[T] = {
    val arangoDB = createArangoBuilder(options).build()
    context.addTaskCompletionListener { c => arangoDB.shutdown() }
    createCursor(arangoDB, options, split.asInstanceOf[ArangoPartition]).asScala
  }

  private def createCursor(arangoDB: ArangoDB, readOptions: ReadOptions, partition: ArangoPartition)(implicit clazz: ClassTag[T]): ArangoCursor[T] =
    arangoDB.db(readOptions.database).query(s"FOR doc IN @@col ${createFilter()} RETURN doc", partition.bindVars.asJava, partition.queryOptions, clazz.runtimeClass.asInstanceOf[Class[T]])

  private def createFilter(): String =
    conditions match {
      case Nil => ""
      case _   => conditions.map { "FILTER ".concat(_) } reduce { (x, y) => x + y }
    }

  /**
   * Adds a filter condition. If used multiple times, the conditions will be combined with a logical AND.
   * @param condition the condition for the filter statement. Use <code>doc</code> inside to reference the document. e.g. <code>"doc.name == 'John'"<code>
   */
  def filter(condition: String): ArangoRDD[T] = new ArangoRDD(sparkContext, options, conditions.:+(condition))

  override def getPartitions: Array[Partition] =
    options.partitioner.createPartitions(options).asInstanceOf[Array[Partition]]

  override def toJavaRDD(): ArangoJavaRDD[T] = new ArangoJavaRDD(this)

}