package com.arangodb.spark
import scala.beans.BeanProperty

case class Url(depth: String)
case class Element(key: String, url:List[Url])

case class Person(name: String, firstname:String)
case class Car(driver: Person)
case class Park(firstCar: Car, secondCar:Car, keeper:Person)