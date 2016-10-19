package com.arangodb.spark.rdd.api.java

import scala.reflect.ClassTag
import org.apache.spark.api.java.JavaRDD
import com.arangodb.spark.rdd.ArangoRDD
import com.arangodb.spark.ArangoSpark
import com.arangodb.spark.WriteOptions

class ArangoJavaRDD[T](override val rdd: ArangoRDD[T])(implicit override val classTag: ClassTag[T]) extends JavaRDD[T](rdd)(classTag) {

}