package com.arangodb.spark

trait ArangoOptions {

  def database: String = "_system"

  def host: Option[String] = None

  def port: Option[Int] = None

  def user: Option[String] = None

  def password: Option[String] = None

}