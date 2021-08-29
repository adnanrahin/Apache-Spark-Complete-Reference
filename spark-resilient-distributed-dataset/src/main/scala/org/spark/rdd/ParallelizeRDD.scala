package org.spark.rdd

import org.apache.spark.sql.SparkSession

object ParallelizeRDD {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("ParallelizeRDD")
      .getOrCreate()

    val sc = spark.sparkContext

    val rdd = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7))

    val rddCollect:Array[Int] = rdd.collect()

    println("Number of Partitions: "+rdd.getNumPartitions)

    println("Action: First element: "+rdd.first())

    println("Action: RDD converted to Array[Int] : ")

    rddCollect.foreach(println)

  }

}
