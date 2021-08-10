package org.spark.apis.introduction

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.desc

object FlightDataSparkSQL {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[*]")
      .appName("FlightDataSparkLoad")
      .getOrCreate()

    val flightData2015 = spark
      .read
      .option("inferSchema", "true")
      .option("header", "true")
      .csv("data/flight-data/csv/2015-summary.csv")

    flightData2015.createOrReplaceTempView("flight_data_2015")

    val sqlWay = spark.sql(
      """
              SELECT DEST_COUNTRY_NAME, count(1)
              FROM flight_data_2015
              GROUP BY DEST_COUNTRY_NAME""")

    println(sqlWay.explain)

    println(sqlWay.show())

    val sqlTake = spark.sql("SELECT max(count) from flight_data_2015").take(1)

    println(sqlTake.mkString("Array(", ", ", ")"))

    val maxSql = spark.sql(
      """
                          SELECT DEST_COUNTRY_NAME, sum(count) as destination_total
                          FROM flight_data_2015
                          GROUP BY DEST_COUNTRY_NAME
                          ORDER BY sum(count) DESC
                          LIMIT 5""")

    println(maxSql.show())

    val topFiveDestinationCountry = flightData2015
      .groupBy("DEST_COUNTRY_NAME")
      .sum("count")
      .withColumnRenamed("sum(Count)", "destination_total")
      .sort(desc("destination_total"))
      .limit(5)

    println(topFiveDestinationCountry.show())
  }

}
