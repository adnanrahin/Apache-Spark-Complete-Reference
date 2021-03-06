package org.apache.spark.spark_in_action.chapter_four_rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object CustomerTransaction {

  def main(args: Array[String]): Unit = {

    /**
     *  Input Parameters
     *
     * args(0) -> textdata\ch04_data_transactions.txt
     * args(1) -> textdata\ch04_data_products.txt
     *
     * */

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("CustomerTransaction")
      .getOrCreate()

    val sc = spark.sparkContext

    val transactionFile = sc.textFile(args(0))

    val transactionData = transactionFile.map(_.split("#"))

    // transactionData.foreach(row => println(row.mkString("Array(", ", ", ")")))

    val transactionByCustomer = transactionData.map(transaction => (transaction(2).toInt, transaction))

    // transactionByCustomer.foreach(row => println(row._2.mkString("Array(", ", ", ")")))

    /** Creating new map to count number of transaction is completed by each customer* */

    val countTransactionByCustomer =
      transactionByCustomer.countByKey()

    //println(countTransactionByCustomer)

    /** Find which customer made the most purchase* */

    val (cid, numberOfPurchase) = transactionByCustomer
      .countByKey().toSeq.maxBy(_._2)

    println("Customer ID: " + cid + " Purchase: " + numberOfPurchase)

    var complementTransaction = Array(Array("2015-03-30", "11:59 PM", "53", "4", "1", "0.00"))

    println("\nTransaction made by customer ID: 53")

    val transactionMadeByCustomer = transactionByCustomer.lookup(53)

    // transactionMadeByCustomer.foreach(row => println(row.mkString("Array(", ", ", ")")))

    val transactionByCustomerUpdate = transactionByCustomer.mapValues(transaction => {
      if (transaction(3).toInt == 25 && transaction(4).toDouble > 1) {
        transaction(5) = (transaction(5).toDouble * 0.95).toString
      }
      transaction
    })

    // transactionByCustomerUpdate.foreach(transaction => println(transaction._2.mkString("Array(", ", ", ")")))

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

    // transactionByCustomerList.foreach(row => println(row._2.mkString("(", ", ", ")")))

    /** Converting amount that customer spend to Double and map them with customer ID* */

    val amounts = transactionByCustomerList.mapValues(t => t(5).toDouble)

    /** Fold all the keys, where all the similar key will be merge* */

    val totals = amounts.foldByKey(0)((p1, p2) => p1 + p2).collect()

    // totals.foreach(row => println(row))

    val (customerIdMost, totalSpend) = totals.sortBy(_._2).toSeq.last

    println("Customer ID: " + customerIdMost + " Total Money Spend: " + totalSpend)

    complementTransaction = complementTransaction :+ Array("2015-03-30", "11:59 PM", "76", "63", "1", "0.00")

    val parallelizedTransactionByCustomer =
      transactionByCustomerList
        .union(sc.parallelize(complementTransaction).map(t => (t(2).toInt, t)))

    // parallelizedTransactionByCustomer.foreach(row => println(row._2.mkString("Array(", ", ", ")")))

    /*parallelizedTransactionByCustomer
      .map(t => t._2
        .mkString("#"))
      .saveAsTextFile("textdata/ch04outputtransByCust")*/

    val prods = parallelizedTransactionByCustomer.aggregateByKey(List[String]())(
      (prods, tran) => prods ::: List(tran(3)),
      (prods1, prods2) => prods1 ::: prods2
    )

    /** Shuffle caused by partitioner removal* */

    val rdd: RDD[Int] = sc.parallelize(1 to 10000)
    val rddMapSwap = rdd.map(x => (x, x * x)).map(_.swap).count()
    val rddMapReduceByKey = rdd.map(x => (x, x * x)).reduceByKey((v1, v2) => v1 + v2).count()

    println("Rdd Map Swap: " + rddMapSwap + " Rdd Map Reduce By Key: " + rddMapReduceByKey)

    val transactionByProd = transactionData.map(transaction => (transaction(3).toInt, transaction))

    /*transactionByProd.foreach(t => println(t._1 + " " + t._2.mkString("Array(", ", ", ")")))*/

    val totalsByProd =
      transactionByProd.mapValues(t => t(5).toDouble)
        .reduceByKey { case (t1, t2) => t1 + t2 }

    /*totalTransactionProd.foreach(t => println(t._1 + " " + t._2))*/

    val products = sc.textFile(args(1))
      .map(line => line.split("#"))
      .map(p => (p(0).toInt, p))

    /*products.foreach(p => println(p._1 + " " + p._2.mkString("Array(", ", ", ")")))*/

    /*println(products.lookup(89).mkString("Array(", ", ", ")"))*/

    /** Spark RDD Joins* */

    val totalsAndProds = totalsByProd.join(products)

    /** Left Outer Join -> Finding product that no one bought* */

    val totalsWithMissingProdsLeftOuterJoin = products.leftOuterJoin(totalsByProd)
    val totalsWIthMissingProdsRightOuterJoin = totalsByProd.rightOuterJoin(products)

    val missingProds = totalsWIthMissingProdsRightOuterJoin
      .filter(x => x._2._1 == None)
      .map(x => x._2._2)

    missingProds.foreach(r => println(r.mkString("Array(", ", ", ")")))

    /** Similar implementations* */

    /*val missingProds = products.subtractByKey(totalsByProd).values*/

    /** Finding the missing product using co-group */

    val prodTotCoGroup = totalsByProd.cogroup(products)

    val filterProdCoGroup = prodTotCoGroup
      .filter(x => x._2._1.isEmpty)

    /*filterProdCoGroup.foreach(x => println(x._2._2.head.mkString("Array(", ", ", ")")))*/

    /** Finding product that not missing* */

    val totalsAndProdsNotMissing = prodTotCoGroup.filter(x => x._2._1.nonEmpty).
      map(x => (x._2._2.head(0).toInt, (x._2._1.head, x._2._2.head)))

    /*totalsAndProdsNotMissing.foreach(x => println(x._2._2.head.mkString("Array(", ", ", ")")))*/


    /** RDD Cartesian Transformation* */

    val rdd1 = sc.parallelize(List(7, 8, 9))
    val rdd2 = sc.parallelize(List(1, 2, 3))

    val cartesianProduct = rdd1.cartesian(rdd2).collect()

    println(cartesianProduct.mkString("Array(", ", ", ")"))

    /** Sorting Data * */

    val sortedProds = totalsAndProds.sortBy(_._2._2(1))

    /*sortedProds.foreach(x => println(x._2._2.head.mkString("Array(", ", ", ")")))*/

    /** Grouping data with the combineByKey transformation * */

    def createComb: Array[String] => (Double, Double, Int, Double) = {
      case (t) => {
        val total = t(5).toDouble
        val q = t(4).toInt
        (total / q, total / q, q, total)
      }
    }

    def mergeVal: ((Double, Double, Int, Double), Array[String]) => (Double, Double, Int, Double) = {
      case ((mn, mx, c, tot), t) => {
        val total = t(5).toDouble
        val q = t(4).toInt
        (scala.math.min(mn, total / q), scala.math.max(mx, total / q), c + q, tot + total)
      }
    }

    def mergeComb: ((Double, Double, Int, Double), (Double, Double, Int, Double)) => (Double, Double, Int, Double) = {
      case ((mn1, mx1, c1, tot1), (mn2, mx2, c2, tot2)) =>
        (scala.math.min(mn1, mn2), scala.math.max(mx1, mx2), c1 + c2, tot1 + tot2)
    }

    val avgByCust = transactionByCustomer.combineByKey(createComb, mergeVal, mergeComb,
      new org.apache.spark.HashPartitioner(transactionByCustomer.partitions.size)).
      mapValues({ case (mn, mx, cnt, tot) => (mn, mx, cnt, tot, tot / cnt) })
    avgByCust.first()

    totalsAndProds.map(_._2).map(x => x._2.mkString("#") + ", " + x._1).saveAsTextFile("textdata/ch04output-totalsPerProd")
    avgByCust.map { case (id, (min, max, cnt, tot, avg)) => "%d#%.2f#%.2f#%d#%.2f#%.2f".format(id, min, max, cnt, tot, avg) }
      .saveAsTextFile("textdata/ch04output-avgByCust")

  }
}
