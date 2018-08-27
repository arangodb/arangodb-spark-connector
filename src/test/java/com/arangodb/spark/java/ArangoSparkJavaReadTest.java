package com.arangodb.spark.java;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.Protocol;
import com.arangodb.spark.ArangoSpark;
import com.arangodb.spark.ReadOptions;
import com.arangodb.spark.rdd.api.java.ArangoJavaRDD;

public class ArangoSparkJavaReadTest {

	private static final String DB = "spark_test_db";
	private static final String COLLECTION = "spark_test_col";
	private static ArangoDB arangoDB;
	private static JavaSparkContext sc;

	@BeforeClass
	public static void setup() {
		arangoDB = new ArangoDB.Builder().build();
		SparkConf conf = new SparkConf(false).setMaster("local")
				.setAppName("test");
		sc = new JavaSparkContext(conf);
		try {
			arangoDB.db(DB).drop();
		} catch (ArangoDBException e) {
		}
		arangoDB.createDatabase(DB);
		arangoDB.db(DB).createCollection(COLLECTION);
		Collection<TestJavaEntity> docs = new ArrayList<>();
		for (int i = 1; i <= 100; i++) {
			docs.add(new TestJavaEntity(i));
		}
		arangoDB.db(DB).collection(COLLECTION).insertDocuments(docs);
	}

	@AfterClass
	public static void teardown() {
		sc.stop();
		arangoDB.db(DB).drop();
		arangoDB.shutdown();
	}
	
	@Test
	public void loadAll() {
		ArangoJavaRDD<TestJavaEntity> rdd = ArangoSpark.load(sc, COLLECTION, new ReadOptions().database(DB), TestJavaEntity.class);
		assertThat(rdd.count(), is(100L));
	}
	
	@Test
	public void loadWithFilterStatement() {
		ArangoJavaRDD<TestJavaEntity> rdd = ArangoSpark.load(sc, COLLECTION, new ReadOptions().database(DB), TestJavaEntity.class);
		ArangoJavaRDD<TestJavaEntity> rdd2 = rdd.filter("doc.test <= 50");
		assertThat(rdd2.count(), is(50L));
	}

	@Test
	public void loadAllWithHTTP() {
		ArangoJavaRDD<TestJavaEntity> rdd = ArangoSpark.load(sc, COLLECTION, new ReadOptions().database(DB).protocol(Protocol.HTTP_JSON), TestJavaEntity.class);
		assertThat(rdd.count(), is(100L));
	}
	
}