package com.arangodb.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.scalatest.BeforeAndAfterAll
import org.scalatest.BeforeAndAfterEach
import org.scalatest.Finders
import org.scalatest.FunSuite
import org.scalatest.Matchers

import com.arangodb.ArangoDB
import com.arangodb.entity.BaseDocument
import com.arangodb.velocypack._
import collection.JavaConversions._
import com.arangodb.ArangoCursor
import com.arangodb.ArangoDBException

class ArangoDBSparkTest extends FunSuite with Matchers with BeforeAndAfterAll with BeforeAndAfterEach {

  val DB = "spark_test_db"
  val COLLECTION = "spark_test_col"
  val arangoDB = new ArangoDB.Builder().build()
  val conf = new SparkConf()
    .setMaster("local")
    .setAppName("ArangoDBSparkTest")
    .set("arangodb.user", "root")
    .set("arangodb.password", "")
  val sc = new SparkContext(conf)

  override def beforeAll() {
    try {
      arangoDB.db(DB).drop()
    } catch {
      case e: ArangoDBException =>
    }
    arangoDB.createDatabase(DB)
    arangoDB.db(DB).createCollection(COLLECTION)
  }

  override def afterAll() {
    arangoDB.db(DB).drop()
    arangoDB.shutdown()
  }

  override def afterEach() {
    arangoDB.db(DB).collection(COLLECTION).truncate()
  }

  test("save RDD to ArangoDB") {
    checkDocumentCount(0)

    val documents = sc.parallelize((1 to 100).map { i => TestEntity(i) })
    ArangoDBSpark.save(documents, COLLECTION, SaveOptions(DB))

    checkDocumentCount(100)
  }

  test("save RDD[VPackSlice] to ArangoDB") {
    checkDocumentCount(0)

    val documents = sc.parallelize((1 to 100).map { i => new VPackBuilder().add(ValueType.OBJECT).add("test", java.lang.Integer.valueOf(i)).close().slice() })
    ArangoDBSpark.save(documents, COLLECTION, SaveOptions(DB))

    checkDocumentCount(100)
  }

  test("save DataFrame to ArangoDB") {
    checkDocumentCount(0)

    val documents = sc.parallelize((1 to 100).map { i => TestEntity(i) })
    val sql: SQLContext = SQLContext.getOrCreate(sc);
    val df = sql.createDataFrame(documents, classOf[TestEntity])
    ArangoDBSpark.save(df, COLLECTION, SaveOptions(DB))

    checkDocumentCount(100)
  }

  test("save Dataset to ArangoDB") {
    checkDocumentCount(0)

    val documents = sc.parallelize((1 to 100).map { i => TestEntity(i) })
    val sql: SQLContext = SQLContext.getOrCreate(sc);
    val encoder = ExpressionEncoder.javaBean(classOf[TestEntity])
    val ds = sql.createDataset(documents)(encoder);
    ArangoDBSpark.save(ds, COLLECTION, SaveOptions(DB))

    checkDocumentCount(100)
  }

  private def checkDocumentCount(count: Int) {
    arangoDB.db(DB).collection(COLLECTION).count().getCount should be(count)
  }

}