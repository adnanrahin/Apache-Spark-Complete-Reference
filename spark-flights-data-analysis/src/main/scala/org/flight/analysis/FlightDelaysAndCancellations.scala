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
      .appName("FlightDelaysAndCancellations")
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    val flightsCsv = sc.textFile("datasource/2015_flights_data/flights.csv")
    val airlineCsv = sc.textFile("datasource/2015_flights_data/airlines.csv")
    val airportCsv = sc.textFile("datasource/2015_flights_data/airports.csv")

    val flightsRDD: RDD[Flight] = loadFlightCsvToRDD(flightsCsv)
    val airlineRDD: RDD[Airline] = loadAirlineToRDD(airlineCsv)
    val airportRDD: RDD[Airport] = loadAirportToRDD(airportCsv)

    val cancelledFlight: RDD[Flight] = findAllTheFlightsGetCancelled(flightsRDD)

    val airlinesCancelledNumberOfFlights = findAirlinesTotalNumberOfFlightsCancelled(cancelledFlight, airlineRDD)

    val numberOfDepartureFlightFromAirport =
      findTotalNumberOfDepartureFlightFromAirport(flightsRDD, airportRDD, "LGA")

    val mostCancelledAirline = findMaxFlightCancelledAirline(flightsRDD, airlineRDD)

    println(mostCancelledAirline)

  }

  def loadFlightCsvToRDD(flightsCSV: RDD[String]): RDD[Flight] = {
    val flightsRDD: RDD[Flight] =
      flightsCSV
        .map(row => row.split(",", -1))
        .map(str => Flight(str(0),
          str(1), str(2), str(3), str(4), str(5), str(6),
          str(7), str(8), str(9), str(10), str(1), str(12),
          str(13), str(14), str(15), str(16), str(17),
          str(18), str(19), str(20), str(21), str(22), str(23),
          str(24), str(25), str(26), str(27), str(28), str(29), str(30))).mapPartitionsWithIndex {
        (idx, iter) => if (idx == 0) iter.drop(1) else iter
      }

    flightsRDD
  }

  def loadAirlineToRDD(airlineCSV: RDD[String]): RDD[Airline] = {
    val airlineRDD: RDD[Airline] =
      airlineCSV
        .map(row => row.split(",", -1))
        .map(str => Airline(str(0), str(1))).mapPartitionsWithIndex {
        (idx, iter) => if (idx == 0) iter.drop(1) else iter
      }

    airlineRDD
  }

  def loadAirportToRDD(airportCSV: RDD[String]): RDD[Airport] = {
    val airportRDD: RDD[Airport] =
      airportCSV
        .map(row => row.split(",", -1))
        .map(str => Airport(str(0), str(1), str(2), str(3), str(4), str(5), str(6)))
        .mapPartitionsWithIndex {
          (idx, iter) => if (idx == 0) iter.drop(1) else iter
        }

    airportRDD
  }

  def findAllTheFlightsGetCancelled(flightsRDD: RDD[Flight]): RDD[Flight] = {
    val cancelledFlight =
      flightsRDD.filter(flight => flight.cancelled.equals("1"))

    cancelledFlight
  }

  def findAirlinesTotalNumberOfFlightsCancelled(cancelledFlight: RDD[Flight], airlineRDD: RDD[Airline]): List[(String, Int)] = {
    val lookupMAP =
      airlineRDD.map(f => (f.iataCode, f.airlineName))
        .collect()
        .toMap

    val airlinesCancelledFlights =
      cancelledFlight
        .groupBy(_.airline)
        .map { iter =>
          lookupMAP.get(iter._1) match {
            case Some(value) => (value, iter._2.toList.size)
            case None => ("Flight IATA Code is wrong", iter._2.toList.size)
          }
        }
        .collect()
        .toList

    airlinesCancelledFlights
  }

  def findTotalNumberOfDepartureFlightFromAirport(flightsRDD: RDD[Flight], airportRDD: RDD[Airport], airportIataCode: String):
  (String, Int) = {

    val airportMap = airportRDD
      .map(airport => (airport.iataCode, airport.airport))
      .collect()
      .toMap


    val notCancelledFlight =
      flightsRDD.filter(flight => flight.cancelled.equals("0"))

    val totalFlight: (String, Int) = notCancelledFlight
      .groupBy(flight => flight.originAirport)
      .filter(flight => flight._1.equals(airportIataCode))
      .map { flight =>
        airportMap.get(flight._1) match {
          case Some(value) => (value, flight._2.toList.size)
          case None => (flight._1, flight._2.toList.size)
        }
      }
      .collect()
      .toList.head

    totalFlight
  }

  def findMaxFlightCancelledAirline(flightsRDD: RDD[Flight], airlineRDD: RDD[Airline]): (String, Int) = {

    val cancelledFlightRDD: RDD[Flight] = findAllTheFlightsGetCancelled(flightsRDD)

    val airlineRDDMap = airlineRDD.map(f => (f.iataCode, f.airlineName)).collect().toMap

    val maxCancelledAirliner = cancelledFlightRDD
      .groupBy(_.airline)
      .map(flight => (flight._1, flight._2.toList.size))
      .sortBy(-_._2).collect().toList

    airlineRDDMap.get(maxCancelledAirliner.head._1) match {
      case Some(value) => (value, maxCancelledAirliner.head._2)
      case None => (s"No Such IATA Code ${maxCancelledAirliner.head._1}", maxCancelledAirliner.head._2)
    }

  }

}
