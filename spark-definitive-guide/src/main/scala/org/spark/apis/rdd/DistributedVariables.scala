package org.spark.apis.rdd

import org.apache.spark.sql.SparkSession

object DistributedVariables {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("DistributedVariables")
      .getOrCreate()

    val myCollection = "Spark The Definitive Guide : Big Data Processing Made Simple"
      .split(" ")

    val words = spark.sparkContext.parallelize(myCollection, 2)

    words.foreach(str => println(str))

    val supplementalData = Map("Spark" -> 1000, "Definitive" -> 2000, "Big" -> -300, "Simple" -> 100)

    val suppBroadcast = spark.sparkContext.broadcast(supplementalData)

    println(suppBroadcast.getClass)

    println(suppBroadcast.value)

    val wordsMapWIthSuppBroadCast =
      words
        .map(word => (word, suppBroadcast.value.getOrElse(word, 0)))
        .sortBy(wordPair => wordPair._2)
        .collect()

    wordsMapWIthSuppBroadCast.foreach(row => println(row))

  }

}
