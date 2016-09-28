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
      host = options.host.orElse(Some(sc.get("arangodb.host", null))),
      port = options.port.orElse(if (sc.contains("arangodb.port")) Some(sc.get("arangodb.port").toInt) else None),
      user = options.user.orElse(Some(sc.get("arangodb.user", null))),
      password = options.password.orElse(Some(sc.get("arangodb.password", null))))
  }

  private[spark] def createWriteOptions(options: WriteOptions, sc: SparkConf): WriteOptions = {
    options.copy(
      host = options.host.orElse(Some(sc.get("arangodb.host", null))),
      port = options.port.orElse(if (sc.contains("arangodb.port")) Some(sc.get("arangodb.port").toInt) else None),
      user = options.user.orElse(Some(sc.get("arangodb.user", null))),
      password = options.password.orElse(Some(sc.get("arangodb.password", null))))
  }

  private[spark] def createArangoBuilder(options: ArangoOptions): ArangoDB.Builder = {
    val builder = new ArangoDB.Builder()
    options.host.foreach { builder.host(_) }
    options.port.foreach { builder.port(_) }
    options.user.foreach { builder.user(_) }
    options.password.foreach { builder.password(_) }
    builder
  }

}