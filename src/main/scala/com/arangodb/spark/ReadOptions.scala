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

package com.arangodb.spark

import com.arangodb.spark.rdd.partition.ArangoPartioner
import com.arangodb.spark.rdd.partition.ArangoDefaultPartitioner

case class ReadOptions(override val database: String = "_system",
                       val collection: Option[String] = None,
                       val query: Option[String] = None,
                       val bindVars: Option[Map[String, Object]] = None,
                       val partitionKey: Option[String] = None,
                       val partitioner: ArangoPartioner = new ArangoDefaultPartitioner(),
                       override val host: Option[String] = None,
                       override val port: Option[Int] = None,
                       override val user: Option[String] = None,
                       override val password: Option[String] = None) extends ArangoOptions {

  def copy(database: String = database,
           collection: Option[String] = collection,
           query: Option[String] = query,
           bindVars: Option[Map[String, Object]] = bindVars,
           partitionKey: Option[String] = partitionKey,
           partitioner: ArangoPartioner = partitioner,
           host: Option[String] = host,
           port: Option[Int] = port,
           user: Option[String] = user,
           password: Option[String] = password): ReadOptions = {
    ReadOptions(database, collection, query, bindVars, partitionKey, partitioner, host, port, user, password)
  }

}