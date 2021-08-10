package org.spark.apis.introduction

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, window}

object RetailDataStreamingDataFrame {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("RetailDataStreamingDataFrame")
      .getOrCreate()

    val streamingDataFrame = spark
      .readStream
      .schema("staticSchema")
      .option("maxFilesPerTrigger", 1)
      .format("csv")
      .option("header", "true")
      .load("data/retail-data/by-day/*.csv")

    val purchaseByCustomerPerHour = streamingDataFrame
      .selectExpr(
        "CustomerId",
        "(UnitPrice * Quantity) as total_cost",
        "InvoiceDate")
      .groupBy(col("CustomerId"), window(col("InvoiceDate"), "1 day"))
      .sum("total_cost")

    println(purchaseByCustomerPerHour.show(10))

  }

}
