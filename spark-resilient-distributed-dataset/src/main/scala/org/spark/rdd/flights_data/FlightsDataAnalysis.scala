package org.spark.rdd.flights_data

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object FlightsDataAnalysis {

  case class Flight(date: String, delay: String, distance: String, origin: String, destination: String)

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder().master("local[*]")
      .appName("FlightsDataAnalysis")
      .getOrCreate()

    val sc = spark.sparkContext

    val readCsv = sc.textFile("datasource/flights/departuredelays.csv")

    val flightsRdd: RDD[Flight] = readCsv.map(row => row.split(","))
      .map(str => Flight(str(0), str(1), str(2), str(3), str(4))).mapPartitionsWithIndex {
      (idx, iter) => if (idx == 0) iter.drop(1) else iter
    }

    println(flightsRdd.first())

  }

}
