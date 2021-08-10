package org.spark.apis.differenttypesodata

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

object DataFrameFilterUsingVariable {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("ConvertingToSparkTypes")
      .getOrCreate()

    val df = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("data/retail-data/by-day/2010-12-01.csv")

    val priceFilter = col("UnitPrice") > 600

    val descripFilter = col("Description").contains("POSTAGE")

    val priceAndDescDf = df.where(col("StockCode").isin("DOT"))
      .where(priceFilter.or(descripFilter))

    println(priceAndDescDf.show(30, truncate = false))

    val dotCodeFilter = col("StockCode") === "DOT"

    val newDfVal = df.withColumn("isExpensive", dotCodeFilter.and(priceFilter.or(descripFilter)))
      .where("isExpensive")
      .select("unitPrice", "isExpensive")

    println(newDfVal.show(50, truncate = true))

  }

}
