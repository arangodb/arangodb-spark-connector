![ArangoDB-Logo](https://docs.arangodb.com/assets/arangodb_logo_2016_inverted.png)

# arangodb-spark-connector

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.arangodb/arangodb-spark-connector/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.arangodb/arangodb-spark-connector)

## Maven

```XML
<dependencies>
  <dependency>
    <groupId>com.arangodb</groupId>
    <artifactId>arangodb-spark-connector</artifactId>
    <version>1.0.2</version>
  </dependency>
	....
</dependencies>
```

## SBT

```Json
libraryDependencies += "com.arangodb" % "arangodb-spark-connector" % "1.0.2"
```

## Learn more

### Configuration

| property-key              | description                            | default value  |
| ------------------------- | -------------------------------------- | -------------- |
| arangodb.hosts            | comma separated list of ArangoDB hosts | 127.0.0.1:8529 |
| arangodb.user             | basic authentication user              | root           |
| arangodb.password         | basic authentication password          |                |
| arangodb.useSsl           | use SSL connection                     | false          |
| arangodb.ssl.keyStoreFile | SSL certificate keystore file          |                |
| arangodb.ssl.passPhrase   | SSL pass phrase                        |                |
| arangodb.ssl.protocol     | SSL protocol                           | TLS            |

### Setup SparkContext

Scala

```Scala
val conf = new SparkConf()
    .set("arangodb.hosts", "127.0.0.1:8529")
    .set("arangodb.user", "myUser")
    .set("arangodb.password", "myPassword")
    ...

val sc = new SparkContext(conf)
```

Java

```Java
SparkConf conf = new SparkConf()
    .set("arangodb.hosts", "127.0.0.1:8529")
    .set("arangodb.user", "myUser")
    .set("arangodb.password", "myPassword");
    ...

JavaSparkContext sc = new JavaSparkContext(conf);
```

### Load data from ArangoDB

Scala

```Scala
val rdd = ArangoSpark.load[MyBean](sc, "myCollection")
```

Java

```Java
ArangoJavaRDD<MyBean> rdd = ArangoSpark.load(sc, "myCollection", MyBean.class);
```

### Load data from ArangoDB with server-side filtering

Scala

```Scala
val rdd = ArangoSpark.load[MyBean](sc, "myCollection").filter("doc.name == 'John'")
```

Java

```Java
ArangoJavaRDD<MyBean> rdd = ArangoSpark.load(sc, "myCollection", MyBean.class).filter("doc.name == 'John'");
```

### Save data (RDD, Dataframe or Dataset) to ArangoDB

```Scala
ArangoSpark.save(rdd, "myCollection")
```

## Learn more

- [ArangoDB](https://www.arangodb.com/)
- [Changelog](ChangeLog.md)
