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
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.scalatest.BeforeAndAfterAll
import org.scalatest.BeforeAndAfterEach
import org.scalatest.FunSuite
import org.scalatest.Matchers
import collection.JavaConverters._
import com.arangodb.ArangoDB
import com.arangodb.ArangoDBException
import com.arangodb.velocypack.VPackBuilder
import com.arangodb.velocypack.ValueType
import scala.reflect.ClassTag
import com.arangodb.spark.rdd.partition.ArangoPartitionerSinglePartition
import org.scalatest.Ignore

@Ignore
class ArangoSparkSSLReadTest extends FunSuite with Matchers with BeforeAndAfterAll with BeforeAndAfterEach with SharedSparkContextSSL {

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
    val documents = sc.parallelize((1 to 100).map { i => TestEntity(i) })
    ArangoSpark.save(documents, COLLECTION, WriteOptions(DB))
  }

  override def afterAll() {
    try {
      arangoDB.db(DB).drop()
      arangoDB.shutdown()
    } finally {
      super.afterAll()
    }
  }

  test("load all documents from collection") {
    val rdd = ArangoSpark.load[TestEntity](sc, COLLECTION, ReadOptions(DB))
    rdd.count() should be(100)
  }

  test("load with custom partionier") {
    val rdd = ArangoSpark.load[TestEntity](sc, COLLECTION, ReadOptions(DB, partitioner = new ArangoPartitionerSinglePartition()))
    rdd.count() should be(100)
  }

  test("load documents from collection with filter statement") {
    val rdd = ArangoSpark.load[TestEntity](sc, COLLECTION, ReadOptions(DB))
    val rdd2 = rdd.filter("doc.test <= 50")
    rdd2.count() should be(50)
  }
}