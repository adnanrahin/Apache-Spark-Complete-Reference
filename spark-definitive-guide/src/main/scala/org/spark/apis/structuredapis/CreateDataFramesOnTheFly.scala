package org.spark.apis.structuredapis

import org.apache.log4j.{Level, Logger}
import org.apache.spark
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

object CreateDataFramesOnTheFly {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("CreateDataFramesOnTheFly")
      .getOrCreate()

    val myManualSchema = new StructType(Array(
      new StructField("some", StringType, true),
      new StructField("col", StringType, true),
      new StructField("names", LongType, false)))
    val myRows = Seq(Row("Hello", null, 1L))

    val myRDD = spark.sparkContext.parallelize(myRows)

    val myDf = spark.createDataFrame(myRDD, myManualSchema)
    println(myDf.show())

  }

}
