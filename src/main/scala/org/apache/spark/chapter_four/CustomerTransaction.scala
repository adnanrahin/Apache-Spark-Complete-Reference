package org.apache.spark.chapter_four

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

    println("\nTransaction made by customer ID: 53")

    val transactionMadeByCustomer = transactionByCustomer.lookup(53)

    transactionMadeByCustomer.foreach(row => println(row.mkString("Array(", ", ", ")")))

    val transactionByCustomerUpdate = transactionByCustomer.mapValues(transaction => {
      if (transaction(3).toInt == 25 && transaction(4).toDouble > 1) {
        transaction(5) = (transaction(5).toDouble * 0.95).toString
      }
      transaction
    })

    transactionByCustomerUpdate.foreach(transaction => println(transaction._2.mkString("Array(", ", ", ")")))

    val transactionByCustomerList =
      transactionByCustomerUpdate.flatMapValues(transaction => {
        if (transaction(3).toInt == 81 && transaction(4).toDouble >= 5) {
          val cloned = transaction.clone()
          cloned(5) = "0.00"
          cloned(3) = "70"
          cloned(4) = "1"
          List(transaction, cloned)
        }
        else {
          List(transaction)
        }
      })

    transactionByCustomerList.foreach(row => println(row._2.mkString("(", ", ", ")")))

    val amounts = transactionByCustomerList.mapValues(t => t(5).toDouble)

    val totals = amounts.foldByKey(0)((p1, p2) => p1 + p2).collect()

  }

}
