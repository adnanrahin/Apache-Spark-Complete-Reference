package org.spark.apis.structuredapis

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

object SortingRow {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("SortingRow")
      .getOrCreate()

    val df = spark.read.format("json")
      .load("data/flight-data/json/2015-summary.json")

    println(df.show(20))

    val dfSortedByCount = df.sort("count")

    println(dfSortedByCount.show(20))

    val dfSortedByCountAndDest = df.orderBy(col("count"), col("DEST_COUNTRY_NAME"))

    println(dfSortedByCountAndDest.show(100))

  }

}
