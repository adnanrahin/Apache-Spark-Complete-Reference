package org.apache.spark.learning_spark.chapter_two

import org.apache.spark.sql.SparkSession

object SparkDriverProgram {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("SparkDriverProgram")
      .getOrCreate()

    val sc = spark.sparkContext



  }
}
