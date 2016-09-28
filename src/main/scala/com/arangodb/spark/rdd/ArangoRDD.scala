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

import scala.reflect.ClassTag

import org.apache.spark.Partition
import org.apache.spark.SparkContext
import org.apache.spark.TaskContext
import org.apache.spark.rdd.RDD

import com.arangodb.spark.ReadOptions
import com.arangodb.spark._
import collection.JavaConverters._
import com.arangodb.ArangoCursor
import com.arangodb.ArangoDB
import org.apache.spark.launcher.SparkClassCommandBuilder
import org.apache.spark.scheduler.SparkListener
import com.arangodb.spark.rdd.partition.ArangoPartition

class ArangoRDD[T: ClassTag](
    @transient override val sparkContext: SparkContext,
    val options: ReadOptions) extends RDD[T](sparkContext, Nil) {

  override def compute(split: Partition, context: TaskContext): Iterator[T] = {
    val arangoDB = createArangoBuilder(options).build()
    context.addTaskCompletionListener { c => arangoDB.shutdown() }
    createCursor(arangoDB, options, split.asInstanceOf[ArangoPartition]).asScala
  }

  private def createCursor(arangoDB: ArangoDB, readOptions: ReadOptions, partition: ArangoPartition)(implicit clazz: ClassTag[T]): ArangoCursor[T] =
    arangoDB.db(readOptions.database).query(partition.query, partition.bindVars.asJava, null, clazz.runtimeClass.asInstanceOf[Class[T]])

  override def getPartitions: Array[Partition] =
    options.partitioner.createPartitions(options).asInstanceOf[Array[Partition]]

}