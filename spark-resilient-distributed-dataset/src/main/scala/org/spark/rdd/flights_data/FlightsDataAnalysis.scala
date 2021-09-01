package org.spark.rdd.flights_data

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object FlightsDataAnalysis {

  case class Flight(date: String, delay: String, distance: String, origin: String, destination: String)

  case class Airport(city: String, state: String, country: String, iata: String)

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    /** Spark Configuration */

    val spark = SparkSession
      .builder().master("local[*]")
      .appName("FlightsDataAnalysis")
      .getOrCreate()

    val sc = spark.sparkContext

    /** Flight CSV and RDD instantiated */

    val readFlightsCsv = sc.textFile("datasource/flights/departuredelays.csv")

    val flightsRdd: RDD[Flight] = readFlightsCsv.map(row => row.split(","))
      .map(str => Flight(str(0), str(1), str(2), str(3), str(4))).mapPartitionsWithIndex {
      (idx, iter) => if (idx == 0) iter.drop(1) else iter
    }

    // flightsRdd.foreach(row => println(row))

    /** @todo find all the flights from ABE -> ATL */

    val allFlightsFromAbeToAtl: RDD[Flight] =
      getAllFlightsFromOriginToDestination("ABE", "ATL", flightsRdd)

    /** @todo find all the flights that delayed origin ABQ */

    val allDelayedABQFlights: RDD[(String, List[Flight])] =
      getAllDelayedFlightsFromOrigin("ABQ", flightsRdd)

    allDelayedABQFlights.foreach(row => println(row._1 + " " + row._2))

  }

  def getAllFlightsFromOriginToDestination(origin: String, destination: String, flightsRdd: RDD[Flight]): RDD[Flight] = {
    flightsRdd
      .filter(flight =>
        flight.origin.equals(origin) && flight.destination.equals(destination))
  }

  def getAllDelayedFlightsFromOrigin(origin: String, flightsRdd: RDD[Flight]): RDD[(String, List[Flight])] = {
    flightsRdd
      .filter(flight => flight.origin.equals("ABQ") && flight.delay.toInt < 0)
      .groupBy(_.origin)
      .map(iter => (iter._1, iter._2.toList))
  }

}
