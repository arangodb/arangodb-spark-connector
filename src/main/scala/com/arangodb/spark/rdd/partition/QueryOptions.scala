package com.arangodb.spark.rdd.partition

case class QueryOptions(shardIds: Array[String]) extends com.arangodb.model.AqlQueryOptions {

}