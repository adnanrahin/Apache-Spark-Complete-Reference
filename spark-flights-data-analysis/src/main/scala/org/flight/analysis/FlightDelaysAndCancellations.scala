package org.flight.analysis

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object FlightDelaysAndCancellations {

  Logger.getLogger("org").setLevel(Level.ERROR)

  case class Airport(iataCode: String,
                     airport: String,
                     city: String,
                     state: String,
                     country: String,
                     latitude: String,
                     longitude: String)

  case class Flight(year: String, month: String, day: String, dayOfWeek: String,
                    airline: String, flightNumber: String, tailNumber: String, originAirport: String,
                    destinationAirport: String, scheduledDeparture: String, departureTime: String,
                    departureDelay: String, taxiOut: String, wheelsOut: String, scheduledTime: String,
                    elapsedTime: String, airTime: String, distance: String, wheelsOn: String, taxiIn: String,
                    scheduledArrival: String, arrivalTime: String, arrivalDelay: String, diverted: String,
                    cancelled: String, cancellationsReason: String, airSystemDelay: String, securityDelay: String,
                    airlineDelay: String, lateAircraftDelay: String, weatherDelay: String)

  case class Airline(iataCode: String, airlineName: String)

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val flightsCsvRead = sc.textFile("datasource/2015_flights_data/flights.csv")

    val flightsRDD: RDD[Flight] =
      flightsCsvRead.map(row => row.split(",", -1))
        .map(str => Flight(str(0),
          str(1), str(2), str(3), str(4), str(5), str(6),
          str(7), str(8), str(9), str(10), str(1), str(12),
          str(13), str(14), str(15), str(16), str(17),
          str(18), str(19), str(20), str(21), str(22), str(23),
          str(24), str(25), str(26), str(27), str(28), str(29), str(30))).mapPartitionsWithIndex {
        (idx, iter) => if (idx == 0) iter.drop(1) else iter
      }

    flightsRDD.foreach(row => println(row))

  }

}
