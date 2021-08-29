package org.spark.rdd

import org.apache.spark.sql.SparkSession

object ParallelizeRDD {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("ParallelizeRDD")
      .getOrCreate()

    val sc = spark.sparkContext

    val rdd = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7))

    

  }

}
