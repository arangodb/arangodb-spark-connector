
![ArangoDB-Logo](https://docs.arangodb.com/assets/arangodb_logo_2016_inverted.png)

# arangodb-spark-connector

## Supported versions

<table>
<tr><th>arangodb-spark-connector</th><th>arangodb-java-driver</th><th>ArangoDB</th></tr>
<tr><td>0.1.x</td><td>4.0.x</td><td>3.1.x</td></tr>
</table>

## Maven, SBT

TODO

## Learn more

### load data from ArangoDB as RDD
```Scala
val rdd = ArangoSpark.load[MyBean](sc, "myCollection", ReadOptions())

```

### save data (RDD, Dataframe or Dataset) to ArangoDB
```Scala
ArangoSpark.save(rdd, "myCollection", WriteOptions())

```