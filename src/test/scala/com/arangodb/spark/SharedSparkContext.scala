package com.arangodb.spark

import org.scalatest.{ BeforeAndAfterAll, BeforeAndAfterEach }
import org.scalatest.Suite
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

/** Shares a local `SparkContext` between all tests in a suite and closes it at the end */
trait SharedSparkContext extends BeforeAndAfterAll { self: Suite =>

  @transient private var _sc: SparkContext = _
  def sc: SparkContext = _sc
  val conf = new SparkConf(false)
    .setMaster("local")
    .setAppName("test")

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