package com.arangodb.spark.rdd.partition

import com.arangodb.spark.ReadOptions

class ArangoPartitionerSinglePartition extends ArangoPartitioner {

  def createPartitions(options: ReadOptions): Array[ArangoPartition] =
    Array(createPartition(0, Array(), options))

}