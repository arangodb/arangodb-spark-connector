package com.arangodb.spark

import com.arangodb.internal.velocystream.Host

trait ArangoOptions {

  def database: String = "_system"

  def hosts: Option[String] = None

  def user: Option[String] = None

  def password: Option[String] = None

}