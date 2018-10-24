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

import scala.util.Try

import org.apache.spark.SparkConf
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import java.io.FileInputStream
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module
import com.arangodb.velocypack.module.scala.VPackScalaModule

package object spark {

  val PropertyHosts = "spark.arangodb.hosts"
  val PropertyUser = "spark.arangodb.user"
  val PropertyPassword = "spark.arangodb.password"
  val PropertyUseSsl = "spark.arangodb.useSsl"
  val PropertySslKeyStoreFile = "spark.arangodb.ssl.keyStoreFile"
  val PropertySslPassPhrase = "spark.arangodb.ssl.passPhrase"
  val PropertySslProtocol = "spark.arangodb.ssl.protocol"
  val PropertyProtocol = "spark.arangodb.protocol"

  def stripSparkPerfix(prop:String):String = prop.replaceFirst("spark.","")

  private[spark] def createReadOptions(options: ReadOptions, sc: SparkConf): ReadOptions = {
    options.copy(
      hosts = options.hosts.orElse(some(sc.get(PropertyHosts,
        sc.get(stripSparkPerfix(PropertyHosts), null)))),
      user = options.user.orElse(some(sc.get(PropertyUser,
        sc.get(stripSparkPerfix(PropertyUser), null)))),
      password = options.password.orElse(some(sc.get(PropertyPassword,
        sc.get(stripSparkPerfix(PropertyPassword), null)))),
      useSsl = options.useSsl.orElse(some(Try(sc.get(PropertyUseSsl,
        sc.get(stripSparkPerfix(PropertyUseSsl), null)).toBoolean).getOrElse(false))),
      sslKeyStoreFile = options.sslKeyStoreFile.orElse(some(sc.get(PropertySslKeyStoreFile,
        sc.get(stripSparkPerfix(PropertySslKeyStoreFile), null)))),
      sslPassPhrase = options.sslPassPhrase.orElse(some(sc.get(PropertySslPassPhrase,
        sc.get(stripSparkPerfix(PropertySslPassPhrase), null)))),
      sslProtocol = options.sslProtocol.orElse(some(sc.get(PropertySslProtocol,
        sc.get(stripSparkPerfix(PropertySslProtocol), null)))),
      protocol = options.protocol.orElse(some(Protocol.valueOf(sc.get(PropertyProtocol,
        sc.get(stripSparkPerfix(PropertyProtocol), "VST")))))
    )
  }

  private[spark] def createWriteOptions(options: WriteOptions, sc: SparkConf): WriteOptions = {
    options.copy(
      hosts = options.hosts.orElse(some(sc.get(PropertyHosts,
        sc.get(stripSparkPerfix(PropertyHosts), null)))),
      user = options.user.orElse(some(sc.get(PropertyUser,
        sc.get(stripSparkPerfix(PropertyUser), null)))),
      password = options.password.orElse(some(sc.get(PropertyPassword,
        sc.get(stripSparkPerfix(PropertyPassword), null)))),
      useSsl = options.useSsl.orElse(some(Try(sc.get(PropertyUseSsl,
        sc.get(stripSparkPerfix(PropertyUseSsl), null)).toBoolean).getOrElse(false))),
      sslKeyStoreFile = options.sslKeyStoreFile.orElse(some(sc.get(PropertySslKeyStoreFile,
        sc.get(stripSparkPerfix(PropertySslKeyStoreFile), null)))),
      sslPassPhrase = options.sslPassPhrase.orElse(some(sc.get(PropertySslPassPhrase,
        sc.get(stripSparkPerfix(PropertySslPassPhrase), null)))),
      sslProtocol = options.sslProtocol.orElse(some(sc.get(PropertySslProtocol,
        sc.get(stripSparkPerfix(PropertySslProtocol), null)))),
      protocol = options.protocol.orElse(some(Protocol.valueOf(sc.get(PropertyProtocol,
        sc.get(stripSparkPerfix(PropertyProtocol), "VST")))))
    )
  }

  private[spark] def createArangoBuilder(options: ArangoOptions): ArangoDB.Builder = {
    val builder = new ArangoDB.Builder()
    builder.registerModules(new VPackJdk8Module, new VPackScalaModule)
    options.hosts.foreach { hosts(_).foreach(host => builder.host(host._1, host._2)) }
    options.user.foreach { builder.user(_) }
    options.password.foreach { builder.password(_) }
    options.useSsl.foreach { builder.useSsl(_) }
    if (options.sslKeyStoreFile.isDefined && options.sslPassPhrase.isDefined) {
      builder.sslContext(createSslContext(options.sslKeyStoreFile.get, options.sslPassPhrase.get, options.sslProtocol.getOrElse("TLS")))
    }
    options.protocol.foreach { builder.useProtocol(_) }
    builder
  }

  private def createSslContext(keyStoreFile: String, passPhrase: String, protocol: String): SSLContext = {
    val ks = KeyStore.getInstance(KeyStore.getDefaultType());
    val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    ks.load(new FileInputStream(keyStoreFile), passPhrase.toCharArray());
    kmf.init(ks, passPhrase.toCharArray());
    val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    tmf.init(ks);
    val sc = SSLContext.getInstance(protocol);
    sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
    sc
  }

  private def some(value: String): Option[String] =
    if (value != null) Some(value) else None

  private def some(value: Boolean): Option[Boolean] =
    Some(value)

  private def some(value: Protocol): Option[Protocol] =
    Some(value)

  private def hosts(hosts: String): List[(String, Int)] =
    hosts.split(",").map({ x =>
      val s = x.split(":")
      if (s.length != 2 || !s(1).matches("[0-9]+"))
        throw new ArangoDBException(s"Could not load property-value arangodb.hosts=${s}. Expected format ip:port,ip:port,...");
      else
        (s(0), s(1).toInt)
    }).toList

}