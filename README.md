![ArangoDB-Logo](https://docs.arangodb.com/assets/arangodb_logo_2016_inverted.png)

# arangodb-spark-connector

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

| property                  | default value  |
|---------------------------|----------------|
| arangodb.hosts            | 127.0.0.1:8529 |
| arangodb.user             | root           |
| arangodb.password         |                |
| arangodb.useSsl           | false          |
| arangodb.ssl.keyStoreFile |                |
| arangodb.ssl.passPhrase   |                |
| arangodb.ssl.protocol     | TLS            |

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