package org.spark.learning.chapter_two

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{Encoders, SparkSession}

object TestRunner {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .appName("BigDataBowlProcessor")
      .master("local[*]")
      .getOrCreate()


    val schema = Encoders.product[PFFScoutingData].schema

    val df = spark.read.format("csv")
      .option("delimiter", ",")
      .option("header", "true")
      .schema(schema)
      .load("C:\\Users\\rahin\\source-code\\Scala\\NFL-Big-Data-Bowl-2022\\dataset\\PFFScoutingData.csv")


    println(df.show(20, truncate = false))

    df.printSchema()

  }
}
