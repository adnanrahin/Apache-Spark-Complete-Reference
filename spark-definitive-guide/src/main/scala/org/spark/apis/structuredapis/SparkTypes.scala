package org.spark.apis.structuredapis

import org.apache.spark.sql.SparkSession

object SparkTypes {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("SparkTypes")
      .getOrCreate()

    val df = spark.range(500).toDF("number")

    val selectDf = df.select(df.col("number") + 10)

    println(df.show(30))

    val rowTest = spark.range(5).toDF().collect()

    println(rowTest.mkString)

  }

}
