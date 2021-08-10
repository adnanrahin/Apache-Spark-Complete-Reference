package org.spark.apis.differenttypesodata

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

object ConvertingToSparkTypes {

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

    val dfTable: Unit = df.createOrReplaceTempView("dfTable")

    println(df.show(20))

    println(dfTable)

    val filterInvoice = df.select("InvoiceNo", "Description")
      .where(col("InvoiceNo").equalTo(536365))

    println(filterInvoice.show(5, truncate = false))



  }

}
