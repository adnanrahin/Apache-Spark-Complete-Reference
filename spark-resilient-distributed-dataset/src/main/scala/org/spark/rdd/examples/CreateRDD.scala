package org.spark.rdd.examples

import org.apache.spark.sql.SparkSession

object CreateRDD {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("CreateRDD")
      .getOrCreate()

    val sc = spark.sparkContext

    val rdd = sc.textFile("textdata/ch04_data_products.txt")

    rdd.foreach(str => println(str))

  }

}
