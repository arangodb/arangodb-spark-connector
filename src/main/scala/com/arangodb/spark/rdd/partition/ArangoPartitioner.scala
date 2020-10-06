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

package com.arangodb.spark.rdd.partition

import com.arangodb.spark.ReadOptions
import com.arangodb.spark._
import com.arangodb.velocystream.Request
import com.arangodb.velocystream.RequestType
import com.arangodb.model.AqlQueryOptions
import com.arangodb.spark.rdd.partition.ArangoPartitioner.ColKey

object ArangoPartitioner {
  val ColKey = "@col"
}

trait ArangoPartitioner extends Serializable {

  def createPartitions(options: ReadOptions): Array[ArangoPartition]

  def createPartition(index: Int, shardIds: Array[String], options: ReadOptions): ArangoPartition = {
    val queryOptions = new AqlQueryOptions()
    shardIds.foreach { queryOptions.shardIds(_) }
    ArangoPartition(index, options, Map[String, Object](ColKey -> options.collection), queryOptions)
  }

}