package org.spark.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object WordCount {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("WordCount")
      .getOrCreate()

    val sc = spark.sparkContext

    val rdd: RDD[String] = sc.textFile("data/text_data.txt")

    val words = rdd.flatMap(f => f.split(" "))

    val wordFrequency = words.map(word => (word, 1)).reduceByKey(_ + _)

    wordFrequency.foreach(println)

    /** @example filter out all the Words start with "a" from words rdd */

    val onlyContainsARdd = words.filter(word => word.startsWith("a") || word.startsWith("A"))

    val countAContainsRddFrequency = onlyContainsARdd.map(word => (word, 1)).reduceByKey(_ + _)

    countAContainsRddFrequency.foreach(f => println(f))

    /** @example sort rdd by key*/

    val sortedWordFrequency = wordFrequency.sortByKey()

    sortedWordFrequency.foreach(println)

  }

}
