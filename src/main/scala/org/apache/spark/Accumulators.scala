package org.apache.spark

import org.apache.spark.sql.{Encoders, SparkSession}

object Accumulators {

  case class Flight(DEST_COUNTRY_NAME: String, ORIGIN_COUNTRY_NAME: String, count: BigInt)

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("Accumulators")
      .getOrCreate()

    /*val df = spark.read.format("parquet")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("hdfs://localhost:9000/data/flight-data/parquet/2010-summary.parquet")

    println(df.show(40))*/

    val flightsDf = spark.read
      .parquet("hdfs://localhost:9000/data/flight-data/parquet/2010-summary.parquet")
      val flights = flightsDf.as(Encoders.bean(Flight.getClass))

    println(flights.show(20))

  }

}
