package com.arangodb.spark

import org.scalatest.{ BeforeAndAfterAll, BeforeAndAfterEach }
import org.scalatest.Suite
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

/** Shares a local `SparkContext` between all tests in a suite and closes it at the end */
trait SharedSparkContextSSL extends BeforeAndAfterAll { self: Suite =>

  @transient private var _sc: SparkContext = _
  def sc: SparkContext = _sc
  val conf = new SparkConf(false)
    .setMaster("local")
    .setAppName("test")
    .set("arangodb.user", "root")
    .set("arangodb.password", "")
    .set("arangodb.hosts", "127.0.0.1:8530")
    .set("arangodb.useSsl", true.toString)
    .set("arangodb.ssl.keyStoreFile", this.getClass().getResource("/example.truststore").getFile())
    .set("arangodb.ssl.passPhrase", "12345678")

  override def beforeAll() {
    super.beforeAll()
    _sc = new SparkContext(conf)
  }

  override def afterAll() {
    try {
      _sc.stop()
      _sc = null
    } finally {
      super.afterAll()
    }
  }

}