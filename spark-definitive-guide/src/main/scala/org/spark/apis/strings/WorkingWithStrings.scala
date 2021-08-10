package org.spark.apis.strings

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, initcap}
import org.apache.spark.sql.functions.{lower, upper}

object WorkingWithStrings {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("WorkingWithString")
      .getOrCreate()

    val df = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("hdfs://localhost:9000/data/retail-data/by-day/2010-12-01.csv")

    val initCap = df.select(initcap(col("Description")))

    println(initCap.show(2))

    val castStringToUpperAndLower = df.select(
      col("Description"),
      lower(col("Description")),
      upper(col("Description"))
    )

    println(castStringToUpperAndLower.show(3))

  }

}
