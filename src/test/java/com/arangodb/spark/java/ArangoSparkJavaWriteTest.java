package com.arangodb.spark.java;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.spark.ArangoSpark;
import com.arangodb.spark.WriteOptions;

public class ArangoSparkJavaWriteTest {

	private static final String DB = "spark_test_db";
	private static final String COLLECTION = "spark_test_col";
	private static ArangoDB arangoDB;
	private static JavaSparkContext sc;

	@BeforeClass
	public static void setup() {
		arangoDB = new ArangoDB.Builder().build();
		SparkConf conf = new SparkConf(false).setMaster("local").set("arangodb.user", "root").set("arangodb.password", "test").setAppName("test");
		sc = new JavaSparkContext(conf);
		try {
			arangoDB.db(DB).drop();
		} catch (ArangoDBException e) {
		}
		arangoDB.createDatabase(DB);
		arangoDB.db(DB).createCollection(COLLECTION);
	}

	@AfterClass
	public static void teardown() {
		sc.stop();
		arangoDB.db(DB).drop();
		arangoDB.shutdown();
	}

	private void checkDocumentCount(Long count) {
		assertThat(arangoDB.db(DB).collection(COLLECTION).count().getCount(),
				is(count));
	}

	@Test
	public void saveJavaRDD() {
		checkDocumentCount(0L);

		List<TestJavaEntity> docs = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			docs.add(new TestJavaEntity());
		}
		JavaRDD<TestJavaEntity> documents = sc.parallelize(docs);
		ArangoSpark.save(documents, COLLECTION, new WriteOptions().database(DB));

		checkDocumentCount(100L);
	}

}
