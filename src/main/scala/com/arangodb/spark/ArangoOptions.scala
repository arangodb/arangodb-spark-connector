package com.arangodb.spark

import javax.net.ssl.SSLContext

trait ArangoOptions {

  def database: String = "_system"

  def hosts: Option[String] = None

  def user: Option[String] = None

  def password: Option[String] = None

  def useSsl: Option[Boolean] = None

  def sslKeyStoreFile: Option[String] = None

  def sslPassPhrase: Option[String] = None

  def sslProtocol: Option[String] = None

}