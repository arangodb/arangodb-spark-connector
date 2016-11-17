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
import scala.reflect.ClassTag

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row

import com.arangodb.spark.rdd.ArangoRDD
import com.arangodb.spark.vpack.VPackUtils
import com.arangodb.spark.rdd.api.java.ArangoJavaRDD
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.api.java.JavaRDD

object ArangoSpark {

  /**
   * Save data from rdd into ArangoDB
   *
   * @param rdd the rdd with the data to save
   * @param collection the collection to save in
   */
  def save[T](rdd: RDD[T], collection: String): Unit =
    save(rdd, collection, WriteOptions())

  /**
   * Save data from rdd into ArangoDB
   *
   * @param rdd the rdd with the data to save
   * @param collection the collection to save in
   * @param options additional write options
   */
  def save[T](rdd: RDD[T], collection: String, options: WriteOptions): Unit =
    saveRDD(rdd, collection, options, (x: Iterator[T]) => x)

  /**
   * Save data from rdd into ArangoDB
   *
   * @param rdd the rdd with the data to save
   * @param collection the collection to save in
   */
  def save[T](rdd: JavaRDD[T], collection: String): Unit =
    saveRDD(rdd.rdd, collection, WriteOptions(), (x: Iterator[T]) => x)

  /**
   * Save data from rdd into ArangoDB
   *
   * @param rdd the rdd with the data to save
   * @param collection the collection to save in
   * @param options additional write options
   */
  def save[T](rdd: JavaRDD[T], collection: String, options: WriteOptions): Unit =
    saveRDD(rdd.rdd, collection, options, (x: Iterator[T]) => x)

  /**
   * Save data from dataset into ArangoDB
   *
   * @param dataset the dataset with data to save
   * @param collection the collection to save in
   */
  def save[T](dataset: Dataset[T], collection: String): Unit =
    saveDF(dataset.toDF(), collection, WriteOptions())

  /**
   * Save data from dataset into ArangoDB
   *
   * @param dataset the dataset with data to save
   * @param collection the collection to save in
   * @param options additional write options
   */
  def save[T](dataset: Dataset[T], collection: String, options: WriteOptions): Unit =
    saveDF(dataset.toDF(), collection, options)

  /**
   * Save data from dataframe into ArangoDB
   *
   * @param dataframe the dataframe with data to save
   * @param collection the collection to save in
   * @param options additional write options
   */
  def saveDF(dataframe: DataFrame, collection: String): Unit =
    saveRDD[Row](dataframe.rdd, collection, WriteOptions(), (x: Iterator[Row]) => x.map { y => VPackUtils.rowToVPack(y) })

  /**
   * Save data from dataframe into ArangoDB
   *
   * @param dataframe the dataframe with data to save
   * @param collection the collection to save in
   * @param options additional write options
   */
  def saveDF(dataframe: DataFrame, collection: String, options: WriteOptions): Unit =
    saveRDD[Row](dataframe.rdd, collection, options, (x: Iterator[Row]) => x.map { y => VPackUtils.rowToVPack(y) })

  private def saveRDD[T](rdd: RDD[T], collection: String, options: WriteOptions, map: Iterator[T] => Iterator[Any]): Unit = {
    val writeOptions = createWriteOptions(options, rdd.sparkContext.getConf)
    rdd.foreachPartition { p =>
      if (p.nonEmpty) {
        val arangoDB = createArangoBuilder(writeOptions).build()
        val col = arangoDB.db(writeOptions.database).collection(collection)
        col.insertDocuments(map(p).toList.asJava)
        arangoDB.shutdown()
      }
    }
  }

  /**
   * Load data from ArangoDB into rdd
   *
   * @param sparkContext the sparkContext containing the ArangoDB configuration
   * @param collection the collection to load data from
   */
  def load[T: ClassTag](sparkContext: SparkContext, collection: String): ArangoRDD[T] =
    load(sparkContext, collection, ReadOptions())

  /**
   * Load data from ArangoDB into rdd
   *
   * @param sparkContext the sparkContext containing the ArangoDB configuration
   * @param collection the collection to load data from
   * @param additional read options
   */
  def load[T: ClassTag](sparkContext: SparkContext, collection: String, options: ReadOptions): ArangoRDD[T] =
    new ArangoRDD[T](sparkContext, createReadOptions(options, sparkContext.getConf).copy(collection = collection))

  /**
   * Load data from ArangoDB into rdd
   *
   * @param sparkContext the sparkContext containing the ArangoDB configuration
   * @param collection the collection to load data from
   * @param additional read options
   */
  def load[T](sparkContext: JavaSparkContext, collection: String, clazz: Class[T]): ArangoJavaRDD[T] = {
    return load(sparkContext, collection, ReadOptions(), clazz)
  }

  /**
   * Load data from ArangoDB into rdd
   *
   * @param sparkContext the sparkContext containing the ArangoDB configuration
   * @param collection the collection to load data from
   * @param additional read options
   */
  def load[T](sparkContext: JavaSparkContext, collection: String, options: ReadOptions, clazz: Class[T]): ArangoJavaRDD[T] = {
    implicit val classtag: ClassTag[T] = ClassTag(clazz)
    return load(sparkContext.sc, collection, options).toJavaRDD()
  }

}
