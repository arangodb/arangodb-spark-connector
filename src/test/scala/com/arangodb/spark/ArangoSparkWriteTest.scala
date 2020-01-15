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

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, Row, SQLContext, SparkSession}
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.scalatest.BeforeAndAfterAll
import org.scalatest.BeforeAndAfterEach
import org.scalatest.FunSuite
import org.scalatest.Matchers
import com.arangodb.ArangoDB
import com.arangodb.ArangoDBException
import com.arangodb.velocypack.VPackBuilder
import com.arangodb.velocypack.ValueType
import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType, StructField, StructType, TimestampType}

class ArangoSparkWriteTest extends FunSuite with Matchers with BeforeAndAfterAll with BeforeAndAfterEach with SharedSparkContext {

  val DB = "spark_test_db"
  val COLLECTION = "spark_test_col"
  val arangoDB = new ArangoDB.Builder().build()

  override def beforeAll() {
    super.beforeAll()
    try {
      arangoDB.db(DB).drop()
    } catch {
      case e: ArangoDBException =>
    }
    arangoDB.createDatabase(DB)
    arangoDB.db(DB).createCollection(COLLECTION)
  }

  override def afterAll() {
    try {
      arangoDB.db(DB).drop()
      arangoDB.shutdown()
    } finally {
      super.afterAll()
    }
  }

  override def afterEach() {
    arangoDB.db(DB).collection(COLLECTION).truncate()
  }

  private def checkDocumentCount(count: Int) {
    arangoDB.db(DB).collection(COLLECTION).count().getCount should be(count)
  }

  test("save RDD to ArangoDB") {
    checkDocumentCount(0)

    val documents = sc.parallelize((1 to 100).map { i => TestEntity(i) })
    ArangoSpark.save(documents, COLLECTION, WriteOptions(DB))

    checkDocumentCount(100)
  }

  test("save RDD[VPackSlice] to ArangoDB") {
    checkDocumentCount(0)

    val documents = sc.parallelize((1 to 100).map { i => new VPackBuilder().add(ValueType.OBJECT).add("test", Integer.valueOf(i)).close().slice() })
    ArangoSpark.save(documents, COLLECTION, WriteOptions(DB))

    checkDocumentCount(100)
  }

  test("save DataFrame to ArangoDB") {
    checkDocumentCount(0)

    val documents = sc.parallelize((1 to 100).map { i => TestEntity(i) })
    val sql: SQLContext = SQLContext.getOrCreate(sc);
    val df = sql.createDataFrame(documents, classOf[TestEntity])
    ArangoSpark.saveDF(df, COLLECTION, WriteOptions(DB))

    checkDocumentCount(100)
  }

  test("save Dataset to ArangoDB") {
    checkDocumentCount(0)

    val documents = sc.parallelize((1 to 100).map { i => TestEntity(i) })
    val sql: SQLContext = SQLContext.getOrCreate(sc);
    val encoder = ExpressionEncoder.javaBean(classOf[TestEntity])
    val ds = sql.createDataset(documents)(encoder);
    ArangoSpark.save(ds, COLLECTION, WriteOptions(DB))

    checkDocumentCount(100)
  }

  test("save a dataframe containing an array of strings to ArangoDB") {
    checkDocumentCount(0)

    val sparkConf = new SparkConf()
      .setAppName("test")
      .set("spark.broadcast.compress", "true")

    // Create Spark Session
    val sparkSession = SparkSession.builder
      .getOrCreate

    val dataTest = Seq(
      Row("1", 8, Array("10", "11", "12")),
      Row("2", 64, Array("13")),
      Row("3", -27, Array("14", "15"))
    )

    val dataSchema = List(
      StructField("_key", StringType, true),
      StructField("number", IntegerType, true),
      StructField("word", ArrayType(StringType, false), false)
    )

    val df: DataFrame = sparkSession.createDataFrame(
      sc.parallelize(dataTest),
      StructType(dataSchema)
    )

    ArangoSpark.saveDF(df, COLLECTION, WriteOptions(DB))

    checkDocumentCount(3)
  }

  test("save a dataframe containing an array of struct to ArangoDB") {
    checkDocumentCount(0)

    val sqlContext= new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._



    val sparkConf = new SparkConf()
      .setAppName("test")
      .set("spark.broadcast.compress", "true")

    // Create Spark Session
    val sparkSession = SparkSession.builder
      .getOrCreate


    val dataTest = Seq(Element("1", List(Url("yes"), null)))
    val df = dataTest.toDF

    ArangoSpark.saveDF(df, COLLECTION, WriteOptions(DB))

    checkDocumentCount(1)
  }

  test("save a dataframe containing a struct of struct to ArangoDB") {
    checkDocumentCount(0)

    val sqlContext= new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._



    val sparkConf = new SparkConf()
      .setAppName("test")
      .set("spark.broadcast.compress", "true")

    // Create Spark Session
    val sparkSession = SparkSession.builder
      .getOrCreate


    val dataTest = Seq(Park(Car(Person("John", "Woo")), Car(Person("Moi", "Coucou")), Person("victor", "Nettoyeur")))
    val df = dataTest.toDF

    ArangoSpark.saveDF(df, COLLECTION, WriteOptions(DB))

    checkDocumentCount(1)
  }

}