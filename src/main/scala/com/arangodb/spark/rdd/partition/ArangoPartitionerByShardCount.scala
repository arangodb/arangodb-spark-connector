package com.arangodb.spark.rdd.partition

import com.arangodb.spark.ReadOptions
import com.arangodb.spark._
import com.arangodb.velocystream.Request
import com.arangodb.velocystream.RequestType

class ArangoPartitionerByShardCount extends ArangoPartitioner {

  override def createPartitions(options: ReadOptions): Array[ArangoPartition] =
    shardIds(options).zipWithIndex map { case (id, index) => createPartition(index, Array(id), options) }

  def shardIds(options: ReadOptions): Array[String] = {
    val arango = createArangoBuilder(options).build()
    val res = arango.execute(new Request(options.database, RequestType.GET, s"/_api/collection/${options.collection}/shards"))
    arango.shutdown()
    arango.util().deserialize(res.getBody.get("shards"), classOf[Array[String]])
  }

}