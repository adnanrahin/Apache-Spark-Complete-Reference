package org.apache.spark.customertransaction

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object CustomerTransaction {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("CustomerTransaction")
      .getOrCreate()

    val sc = spark.sparkContext

    val transactionFile = sc.textFile("textdata/ch04_data_transactions.txt")

    val transactionData = transactionFile.map(_.split("#"))

    transactionData.foreach(row => println(row.mkString("Array(", ", ", ")")))

    val transactionByCustomer = transactionData.map(transaction => (transaction(2).toInt, transaction))

    transactionByCustomer.foreach(row => println(row._2.mkString("Array(", ", ", ")")))

    /** Creating new map to count number of transaction is completed by each customer* */

    val countTransactionByCustomer =
      transactionByCustomer.countByKey()

    println(countTransactionByCustomer)

    /** Find which customer made the most purchase* */

    val (cid, numberOfPurchase) = transactionByCustomer
      .countByKey().toSeq.maxBy(_._2)

    println("Customer ID: " + cid + " Purchase: " + numberOfPurchase)

    var complementTransaction = Array(Array("2015-03-30", "11:59 PM", "53", "4", "1", "0.00"))

    println("\n Transaction made by customer ID: 53")

    val transactionMadeByCustomer = transactionByCustomer.lookup(53)

    transactionMadeByCustomer.foreach(row => println(row.mkString("Array(", ", ", ")")))

  }

}
