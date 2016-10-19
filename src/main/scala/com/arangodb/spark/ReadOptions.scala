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
                       val collection: String = null,
                       val partitioner: ArangoPartioner = new ArangoDefaultPartitioner(),
                       override val host: Option[String] = None,
                       override val port: Option[Int] = None,
                       override val user: Option[String] = None,
                       override val password: Option[String] = None) extends ArangoOptions {

  def this() = this(database = "_system")

  def database(database: String): ReadOptions = copy(database = database)

  def collection(collection: String): ReadOptions = copy(collection = collection)

  def host(host: String): ReadOptions = copy(host = Some(host))

  def port(port: Int): ReadOptions = copy(port = Some(port))

  def user(user: String): ReadOptions = copy(user = Some(user))

  def password(password: String): ReadOptions = copy(password = Some(password))

  def copy(database: String = database,
           collection: String = collection,
           partitioner: ArangoPartioner = partitioner,
           host: Option[String] = host,
           port: Option[Int] = port,
           user: Option[String] = user,
           password: Option[String] = password): ReadOptions = {
    ReadOptions(database, collection, partitioner, host, port, user, password)
  }

}