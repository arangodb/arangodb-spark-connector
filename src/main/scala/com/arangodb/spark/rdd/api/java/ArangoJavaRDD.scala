package com.arangodb.spark.rdd.api.java

import scala.reflect.ClassTag
import org.apache.spark.api.java.JavaRDD
import com.arangodb.spark.rdd.ArangoRDD
import com.arangodb.spark.ArangoSpark
import com.arangodb.spark.WriteOptions

class ArangoJavaRDD[T](override val rdd: ArangoRDD[T])(implicit override val classTag: ClassTag[T]) extends JavaRDD[T](rdd)(classTag) {

  /**
   * Adds a filter condition. If used multiple times, the conditions will be combined with a logical AND.
   * @param condition the condition for the filter statement. Use <code>doc</code> inside to reference the document. e.g. <code>"doc.name == 'John'"<code>
   */
  def filter(condition: String): ArangoJavaRDD[T] = new ArangoJavaRDD[T](rdd.filter(condition))

}