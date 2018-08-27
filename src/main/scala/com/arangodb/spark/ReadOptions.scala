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

import com.arangodb.spark.rdd.partition.ArangoDefaultPartitioner
import com.arangodb.spark.rdd.partition.ArangoPartitioner

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLProtocolException
import com.arangodb.Protocol

case class ReadOptions(override val database: String = "_system",
                       val collection: String = null,
                       val partitioner: ArangoPartitioner = new ArangoDefaultPartitioner(),
                       override val hosts: Option[String] = None,
                       override val user: Option[String] = None,
                       override val password: Option[String] = None,
                       override val useSsl: Option[Boolean] = None,
                       override val sslKeyStoreFile: Option[String] = None,
                       override val sslPassPhrase: Option[String] = None,
                       override val sslProtocol: Option[String] = None,
                       override val protocol: Option[Protocol] = None) extends ArangoOptions {

  def this() = this(database = "_system")

  def database(database: String): ReadOptions = copy(database = database)

  def collection(collection: String): ReadOptions = copy(collection = collection)

  def hosts(hosts: String): ReadOptions = copy(hosts = Some(hosts))

  def user(user: String): ReadOptions = copy(user = Some(user))

  def password(password: String): ReadOptions = copy(password = Some(password))

  def useSsl(useSsl: Boolean): ReadOptions = copy(useSsl = Some(useSsl))

  def sslKeyStoreFile(sslKeyStoreFile: String): ReadOptions = copy(sslKeyStoreFile = Some(sslKeyStoreFile))

  def sslPassPhrase(sslPassPhrase: String): ReadOptions = copy(sslPassPhrase = Some(sslPassPhrase))

  def sslProtocol(sslProtocol: String): ReadOptions = copy(sslProtocol = Some(sslProtocol))

  def protocol(protocol: Protocol): ReadOptions = copy(protocol = Some(protocol));

  def copy(database: String = database,
           collection: String = collection,
           partitioner: ArangoPartitioner = partitioner,
           hosts: Option[String] = hosts,
           user: Option[String] = user,
           password: Option[String] = password,
           useSsl: Option[Boolean] = useSsl,
           sslKeyStoreFile: Option[String] = sslKeyStoreFile,
           sslPassPhrase: Option[String] = sslPassPhrase,
           sslProtocol: Option[String] = sslProtocol,
           protocol: Option[Protocol] = protocol): ReadOptions = {
    ReadOptions(database, collection, partitioner, hosts, user, password, useSsl, sslKeyStoreFile, sslPassPhrase, sslProtocol, protocol)
  }

}
