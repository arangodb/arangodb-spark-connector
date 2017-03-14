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

package com.arangodb

import org.apache.spark.SparkConf

package object spark {

  private[spark] def createReadOptions(options: ReadOptions, sc: SparkConf): ReadOptions = {
    options.copy(
      hosts = options.hosts.orElse(some(sc.get("arangodb.hosts", null))),
      user = options.user.orElse(some(sc.get("arangodb.user", null))),
      password = options.password.orElse(some(sc.get("arangodb.password", null))))
  }

  private[spark] def createWriteOptions(options: WriteOptions, sc: SparkConf): WriteOptions = {
    options.copy(
      hosts = options.hosts.orElse(some(sc.get("arangodb.hosts", null))),
      user = options.user.orElse(some(sc.get("arangodb.user", null))),
      password = options.password.orElse(some(sc.get("arangodb.password", null))))
  }

  private[spark] def createArangoBuilder(options: ArangoOptions): ArangoDB.Builder = {
    val builder = new ArangoDB.Builder()
    options.hosts.foreach { hosts(_).foreach(host => builder.host(host._1, host._2)) }
    options.user.foreach { builder.user(_) }
    options.password.foreach { builder.password(_) }
    builder
  }

  private def some(value: String): Option[String] =
    if (value != null) Some(value) else None

  private def hosts(hosts: String): List[(String, Int)] =
    hosts.split(",").map({ x =>
      val s = x.split(":")
      if (s.length != 2 || !s(1).matches("[0-9]+"))
        throw new ArangoDBException(s"Could not load property-value arangodb.hosts=${s}. Expected format ip:port,ip:port,...");
      else
        (s(0), s(1).toInt)
    }).toList

}