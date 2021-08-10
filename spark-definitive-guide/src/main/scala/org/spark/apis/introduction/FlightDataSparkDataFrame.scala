package org.spark.apis.introduction

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.max

object FlightDataSparkDataFrame {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[*]")
      .appName("FlightDataSparkLoad")
      .getOrCreate()

    val flightData2015 = spark
      .read
      .option("inferSchema", "true")
      .option("header", "true")
      .csv("data/flight-data/csv/2015-summary.csv")

    println(flightData2015.take(3).mkString("Array(", ", ", ")"))

    println(flightData2015.sort("count").explain())

    val dataFrameWay = flightData2015
      .groupBy("DEST_COUNTRY_NAME")
      .count()

    println(dataFrameWay.explain)


    val dataFrameTake = flightData2015.select(max("count")).take(1)

    println(dataFrameTake.mkString("Array(", ", ", ")"))

  }

}
