package org.spark.rdd.flights_data

import org.apache.spark.sql.SparkSession

object FlightsDataAnalysis {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder().master("local[*]")
      .appName("FlightsDataAnalysis")
      .getOrCreate()

    val sc = spark.sparkContext



  }

}
