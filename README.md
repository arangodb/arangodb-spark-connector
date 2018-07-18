![ArangoDB-Logo](https://docs.arangodb.com/assets/arangodb_logo_2016_inverted.png)

# arangodb-spark-connector

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.arangodb/arangodb-spark-connector/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.arangodb/arangodb-spark-connector)

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
