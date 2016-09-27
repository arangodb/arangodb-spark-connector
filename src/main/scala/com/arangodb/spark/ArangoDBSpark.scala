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

import scala.collection.JavaConverters._
import org.apache.spark.rdd.RDD
import com.arangodb.ArangoDB
import scala.reflect.ClassTag
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.DataFrame
import com.arangodb.velocypack.VPackSlice
import org.apache.spark.sql.Row
import com.arangodb.spark.vpack.VPackUtils

object ArangoDBSpark {

  private val INSERT_DOCUMENTS_BATCH_SIZE = 1024

  def save[T](rdd: RDD[T], collection: String): Unit =
    save(rdd, collection, SaveOptions())

  def save[T](rdd: RDD[T], collection: String, options: SaveOptions): Unit =
    saveIntern(rdd, collection, options, (x: Iterator[T]) => x)

  def save[T](dataset: Dataset[T], collection: String): Unit =
    save(dataset, collection, SaveOptions())

  def save[T](dataset: Dataset[T], collection: String, options: SaveOptions): Unit =
    save(dataset.toDF(), collection, options)

  def save(dataframe: DataFrame, collection: String): Unit =
    save(dataframe, collection, SaveOptions())

  def save(dataframe: DataFrame, collection: String, options: SaveOptions): Unit =
    saveIntern[Row](dataframe.rdd, collection, options, (x: Iterator[Row]) => x.map { y => VPackUtils.rowToVPack(y) })

  private def saveIntern[T](rdd: RDD[T], collection: String, options: SaveOptions, map: Iterator[T] => Iterator[Any]): Unit = {
    val saveOptions = createSaveOptions(options, rdd.sparkContext.getConf)
    rdd.foreachPartition { p =>
      if (p.nonEmpty) {
        val arangoDB = createArangoBuilder(saveOptions).build()
        val col = arangoDB.db(saveOptions.database).collection(collection)
        map(p).grouped(INSERT_DOCUMENTS_BATCH_SIZE).foreach(b => col.insertDocuments(b.toList.asJava))
        arangoDB.shutdown()
      }
    }
  }

  private def createSaveOptions(options: SaveOptions, sc: SparkConf): SaveOptions = {
    SaveOptions(options.database,
      options.host.orElse(Some(sc.get("arangodb.host", null))),
      options.port.orElse(if (sc.contains("arangodb.port")) Some(sc.get("arangodb.port").toInt) else None),
      options.user.orElse(Some(sc.get("arangodb.user", null))),
      options.password.orElse(Some(sc.get("arangodb.password", null))))
  }

  private def createArangoBuilder(options: SaveOptions): ArangoDB.Builder = {
    val builder = new ArangoDB.Builder()
    options.host.foreach { builder.host(_) }
    options.port.foreach { builder.port(_) }
    options.user.foreach { builder.user(_) }
    options.password.foreach { builder.password(_) }
    builder
  }

}

case class SaveOptions(database: String = "_system", host: Option[String] = None, port: Option[Int] = None, user: Option[String] = None, password: Option[String] = None)
