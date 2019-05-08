package com.arangodb.spark.java;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.BeforeClass;
import org.junit.Test;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.spark.ArangoSpark;
import com.arangodb.spark.WriteOptions;

public class RetestIssuesTest {

	private static final String DB = "spark_test_db";
	
	private static final String COLLECTION = "spark_test_col";
	private static final String COLLECTION2 = "spark_test_col2";
	
	private static ArangoDB arangoDB;
	private static JavaSparkContext sc;
	
	@BeforeClass
	public static void setup() {
		
		arangoDB = new ArangoDB.Builder().build();
		SparkConf conf = new SparkConf(false).setMaster("local").setAppName("test");
		
		sc = new JavaSparkContext(conf);
		
		try {
			arangoDB.db(DB).drop();
		} catch (ArangoDBException e) {
			
		}
		
		arangoDB.createDatabase(DB);
		arangoDB.db(DB).createCollection(COLLECTION);
		arangoDB.db(DB).createCollection(COLLECTION2);
		
	}
	
	@Test
	public void retestPhoneAddr() throws InterruptedException {

		List<TestPhoneEntity> phones = new ArrayList<>();
		
		for (int i = 0; i < 100; i++) {
			
			TestPhoneEntity tpe = new TestPhoneEntity();
			
			phones.add(tpe);
		}
		
		JavaRDD<TestPhoneEntity> documents = sc.parallelize(phones);
		ArangoSpark.save(documents, COLLECTION, new WriteOptions().database(DB));
		assertTrue(documents.collect().size() == 100);
		
		assertTrue(arangoDB.db(DB).collection(COLLECTION).count().getCount() == 100);
		
	}
	
	@Test
	public void retestPhoneAddr2() throws InterruptedException, IOException {

		InputStream testfileStream = this.getClass().getClassLoader().getResourceAsStream("retestPhoneAddr2.json");
		String testJson = IOUtils.toString(testfileStream);
		
		ArrayList<String> list = new ArrayList<String>();
		list.add(testJson);
		
		JavaRDD<String> documents = sc.parallelize(list);
		ArangoSpark.save(documents, COLLECTION2, new WriteOptions().database(DB));
		
		assertTrue(arangoDB.db(DB).collection(COLLECTION2).count().getCount() == 1);
		
	}
	
}
