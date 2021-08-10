package org.spark.apis.introduction

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, window}

object RetailStaticDataFrame {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("RetailStaticDataSet")
      .getOrCreate()

    val staticDataFrame = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("data/retail-data/by-day/*.csv")

    staticDataFrame.createOrReplaceTempView("retail_data")

    val staticSchema = staticDataFrame.schema

    staticSchema.foreach(schema => println(schema.toString()))

    val timeSeriesData = staticDataFrame
      .selectExpr("CustomerID", "(UnitPrice * Quantity) as total_cost", "InvoiceDate")
      .groupBy(col("CustomerId"), window(col("InvoiceDate"), "1 day"))
      .sum("total_cost")

    println(timeSeriesData.show)


    val streamingDataFrame = spark
      .readStream
      .schema(staticSchema)
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

    purchaseByCustomerPerHour.writeStream
      .format("memory") // memory = store in-memory table
      .queryName("customer_purchases") // the name of the in-memory table
      .outputMode("complete") // complete = all the counts should be in the table
      .start()

    val customerPurchase = spark.sql(
      """
                        SELECT * FROM customer_purchases ORDER BY `sum(total_cost)` DESC """)

    println(customerPurchase.show(5))

  }

}
