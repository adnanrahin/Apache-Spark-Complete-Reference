package org.spark.apis.introduction

import org.apache.spark.sql.SparkSession

object FlightDataSparkDataSet {

  case class Flight(DEST_COUNTRY_NAME: String, ORIGIN_COUNTRY_NAME: String, count: BigInt)

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("FlightDataSparkDataSet")
      .getOrCreate()

    import spark.implicits._

    val flightsDF = spark.read.parquet("data/flight-data/parquet/2010-summary.parquet/")
    val flights = flightsDF.as[Flight]

    val filterCanada = flights // take and collect automatically cast type for dataset
      .filter(row => row.ORIGIN_COUNTRY_NAME != "Canada")
      .map(row => row)
      .take(5)

    val explicitTypeCast = flights // type casting more explicit way
      .take(5)
      .filter(flight_row => flight_row.ORIGIN_COUNTRY_NAME != "Canada")
      .map(fr => Flight(fr.DEST_COUNTRY_NAME, fr.ORIGIN_COUNTRY_NAME, fr.count + 5))

    println(filterCanada.mkString("Array(", ", ", ")"))
    println(explicitTypeCast.mkString("Array(", ", ", ")"))

  }

}
