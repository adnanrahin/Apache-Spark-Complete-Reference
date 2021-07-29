package org.apache.spark.customertransaction

import org.apache.spark.sql.SparkSession

object CustomerTransaction {

  def main(args: Array[String]): Unit = {

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

    val (cid, purchase) = transactionByCustomer
      .countByKey().toSeq.maxBy(_._2)

    println("Customer ID: " + cid + " Purchase: " + purchase)

  }

}
