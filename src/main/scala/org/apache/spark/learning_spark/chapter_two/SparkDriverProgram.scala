package org.apache.spark.learning_spark.chapter_two

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.column

object SparkDriverProgram {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("SparkDriverProgram")
      .getOrCreate()

    val sc = spark.sparkContext

    if (args.length < 1) {
      print("Usage: MnMCount <mnm_file_dataset>")
      sys.exit(1)
    }

    val mnmFile = args(0)

    /**
     * Loading data into Spark DataFrame
     * */

    val mnmDf = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(mnmFile)

    println(mnmDf.show(5))

    /**
     *  Aggregate counts of all colors and groupBy() state and Color
     *  OrderBy() in descending order
     * */

    val countMndMDF = mnmDf
      .select(column("State"), column("Color"), column("Count"))


  }
}
