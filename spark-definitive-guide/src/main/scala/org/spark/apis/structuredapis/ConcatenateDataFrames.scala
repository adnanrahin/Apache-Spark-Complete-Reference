package org.spark.apis.structuredapis

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Row, SparkSession}

object ConcatenateDataFrames {

  /**
   * DataFrames are immutable. This means users cannot
   * append to DataFrames because that would be changing it. To append to a DataFrame, you must
   * union the original DataFrame along with the new DataFrame. This just concatenates the twoDataFrames.
   * To union two DataFrames, you must be sure that they have the same schema and
   * number of columns; otherwise, the union will fail.
   * */

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("ConcatenateDataFrames")
      .getOrCreate()

    val df = spark.read.format("json")
      .load("data/flight-data/json/2015-summary.json")

    println(df.show(30))

    val newRows = Seq(Row("Bangladesh", "Bangladesh", 10L), Row("Cybertron", "Bangladesh", 10L))

    val schema = df.schema

    val parallelizedRows = spark.sparkContext.parallelize(newRows)

    val newDataFrame = spark.createDataFrame(parallelizedRows, schema)

    val unionDf = df.union(newDataFrame)

    val findBangladesh = unionDf.filter(col("ORIGIN_COUNTRY_NAME") === "Bangladesh" or
      col("DEST_COUNTRY_NAME") === "Bangladesh")

    println(findBangladesh.show())

  }

}
