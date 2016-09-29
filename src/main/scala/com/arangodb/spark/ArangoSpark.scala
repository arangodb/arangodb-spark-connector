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

package com.arangodb.spark

import scala.collection.JavaConverters.seqAsJavaListConverter

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row

import com.arangodb.spark.vpack.VPackUtils
import com.arangodb.spark.rdd.ArangoRDD
import org.apache.spark.SparkContext
import scala.reflect.ClassTag

object ArangoSpark {

  private val InsertDocumentsBatchSize = 1024

  def save[T](rdd: RDD[T], collection: String): Unit =
    save(rdd, collection, WriteOptions())

  def save[T](rdd: RDD[T], collection: String, options: WriteOptions): Unit =
    saveIntern(rdd, collection, options, (x: Iterator[T]) => x)

  def save[T](dataset: Dataset[T], collection: String): Unit =
    save(dataset, collection, WriteOptions())

  def save[T](dataset: Dataset[T], collection: String, options: WriteOptions): Unit =
    save(dataset.toDF(), collection, options)

  def save(dataframe: DataFrame, collection: String): Unit =
    save(dataframe, collection, WriteOptions())

  def save(dataframe: DataFrame, collection: String, options: WriteOptions): Unit =
    saveIntern[Row](dataframe.rdd, collection, options, (x: Iterator[Row]) => x.map { y => VPackUtils.rowToVPack(y) })

  private def saveIntern[T](rdd: RDD[T], collection: String, options: WriteOptions, map: Iterator[T] => Iterator[Any]): Unit = {
    val writeOptions = createWriteOptions(options, rdd.sparkContext.getConf)
    rdd.foreachPartition { p =>
      if (p.nonEmpty) {
        val arangoDB = createArangoBuilder(writeOptions).build()
        val col = arangoDB.db(writeOptions.database).collection(collection)
        map(p).grouped(InsertDocumentsBatchSize).foreach(b => col.insertDocuments(b.toList.asJava))
        arangoDB.shutdown()
      }
    }
  }

  def load[T: ClassTag](sparkContext: SparkContext, collection: String): ArangoRDD[T] =
    load(sparkContext, collection, ReadOptions())

  def load[T: ClassTag](sparkContext: SparkContext, collection: String, options: ReadOptions): ArangoRDD[T] =
    new ArangoRDD[T](sparkContext, createReadOptions(options, sparkContext.getConf).copy(collection = collection))

}
