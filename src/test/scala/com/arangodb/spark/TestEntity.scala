package com.arangodb.spark

import scala.beans.BeanProperty

case class TestEntity(@BeanProperty var test: Int = Int.MaxValue,
                      @BeanProperty var booleanValue: Boolean = true,
                      @BeanProperty var doubleValue: Double = Double.MaxValue,
                      @BeanProperty var floatValue: Float = Float.MaxValue,
                      @BeanProperty var longValue: Long = Long.MaxValue,
                      @BeanProperty var intValue: Int = Int.MaxValue,
                      @BeanProperty var shortValue: Short = Short.MaxValue,
                      @BeanProperty var stringValue: String = "test",
                      @BeanProperty var sqlDateValue: java.sql.Date = new java.sql.Date(1474988621),
                      @BeanProperty var sqlTimestampValue: java.sql.Timestamp = new java.sql.Timestamp(1474988621)) {

}