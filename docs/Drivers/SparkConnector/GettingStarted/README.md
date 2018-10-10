# ArangoDB Spark Connector - Getting Started

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

## Configuration

| property-key              | description                            | default value  |
| ------------------------- | -------------------------------------- | -------------- |
| spark.arangodb.hosts            | comma separated list of ArangoDB hosts | 127.0.0.1:8529 |
| spark.arangodb.user             | basic authentication user              | root           |
| spark.arangodb.password         | basic authentication password          |                |
| spark.arangodb.protocol         | network protocol                       | VST            |
| spark.arangodb.useSsl           | use SSL connection                     | false          |
| spark.arangodb.ssl.keyStoreFile | SSL certificate keystore file          |                |
| spark.arangodb.ssl.passPhrase   | SSL pass phrase                        |                |
| spark.arangodb.ssl.protocol     | SSL protocol                           | TLS            |

## Setup SparkContext

**Scala**

```Scala
val conf = new SparkConf()
    .set("spark.arangodb.hosts", "127.0.0.1:8529")
    .set("spark.arangodb.user", "myUser")
    .set("spark.arangodb.password", "myPassword")
    ...

val sc = new SparkContext(conf)
```

**Java**

```Java
SparkConf conf = new SparkConf()
    .set("spark.arangodb.hosts", "127.0.0.1:8529")
    .set("spark.arangodb.user", "myUser")
    .set("spark.arangodb.password", "myPassword");
    ...

JavaSparkContext sc = new JavaSparkContext(conf);
```
