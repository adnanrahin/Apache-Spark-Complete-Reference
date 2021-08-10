package org.spark.apis.aggregations

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{sum, count, avg, expr}

object AggregateFunctions {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("WorkingWithString")
      .getOrCreate()

    val df = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("hdfs://localhost:9000/data/retail-data/by-day/*.csv").coalesce(5)

    println(df.show(20))

    val dfTable: Unit = df.createOrReplaceTempView("dfTable")

    println(df.count())

    val countNumberOfDfRow = df.select(count("StockCode"))
    println(countNumberOfDfRow.show(10))


    /*Spark Average*/

    val avgDf = df.select(
      count("Quantity").alias("total_transactions"),
      sum("Quantity").alias("total_purchases"),
      avg("Quantity").alias("average_purchases"),
        expr("mean(Quantity)").alias("mean_purchases")
    )

    println(avgDf.show(5))

  }

}
