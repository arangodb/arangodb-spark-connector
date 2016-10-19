
![ArangoDB-Logo](https://docs.arangodb.com/assets/arangodb_logo_2016_inverted.png)

# arangodb-spark-connector

## Supported versions

<table>
<tr><th>arangodb-spark-connector</th><th>arangodb-java-driver</th><th>ArangoDB</th></tr>
<tr><td>1.0.0-beta1</td><td>4.0.x</td><td>3.1.x</td></tr>
</table>

## Maven

```XML
<dependencies>
  <dependency>
    <groupId>com.arangodb</groupId>
    <artifactId>arangodb-spark-connector</artifactId>
    <version>1.0.0-beta1</version>
  </dependency>
	....
</dependencies>
```

## Learn more

### Setup SparkContext
Scala
```Scala
val conf = new SparkConf()
    .set("arangodb.host", "127.0.0.1")
    .set("arangodb.port", "8529")
    .set("arangodb.user", "myUser")
    .set("arangodb.password", "myPassword")
    ...
    
val sc = new SparkContext(conf)
```
Java
```Java
SparkConf conf = new SparkConf()
    .set("arangodb.host", "127.0.0.1")
    .set("arangodb.port", "8529")
    .set("arangodb.user", "myUser")
    .set("arangodb.password", "myPassword")
    ...
    
JavaSparkContext sc = new JavaSparkContext(conf)
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

### Save data (RDD, Dataframe or Dataset) to ArangoDB
```Scala
ArangoSpark.save(rdd, "myCollection")

```