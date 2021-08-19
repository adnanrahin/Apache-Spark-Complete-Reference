package org.apache.spark.spark_in_action.working_with_rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object SparkRddIntro {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("SparkRddIntro")
      .getOrCreate()

    val sc = spark.sparkContext

    val list1: List[(Int, String)] = List((1, "megatron"), (2, "Optimus"), (3, "Prime"))
    val list2: List[(Int, Int)] = List((1, 99), (2, 98), (3, 97))

    val rdd1: RDD[(Int, String)] = sc.parallelize(list1)
    val rdd2: RDD[(Int, Int)] = sc.parallelize(list2)

    rdd1.foreach(r => println(r._1 + " " + r._2))

    rdd2.foreach(r => println(r._1 + " " + r._2))

  }

}
