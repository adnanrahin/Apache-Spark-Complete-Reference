package org.spark.apis.structuredapis

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, expr}

object DataFrameFilter {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("LoadingJsonInDataFrames")
      .getOrCreate()

    val df = spark.read.format("json")
      .load("data/flight-data/json/2015-summary.json")

    df.printSchema()

    df.foreach(row => println(row))

    val filterByCountryName = df.where(col("ORIGIN_COUNTRY_NAME").startsWith("C"))

    println(filterByCountryName.show(30))

    val withInCountry = df.withColumn("WithInCountry", expr("ORIGIN_COUNTRY_NAME == DEST_COUNTRY_NAME"))

    println(withInCountry.show(20))

    /*
    * Filter Data Frame
    * */

    val filterCountTwo = df.filter(col("count") < 2)

    println(filterCountTwo.show(10))

    println(df.where("count < 2").show(2))

    val multipleExpression = df.where("count < 2")
      .where(col("ORIGIN_COUNTRY_NAME") =!= "Croatia")

    println(multipleExpression.show(3))

    val distinctRows = df.select("ORIGIN_COUNTRY_NAME").distinct()

    println(distinctRows.show(10))

  }

}
