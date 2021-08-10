package org.spark.apis.introduction

import org.apache.spark.sql.SparkSession

object HelloWorld {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .master("local[*]")
      .appName("HelloWorld")
      .getOrCreate()

    val myRange = spark.range(1000).toDF("number")

    myRange.foreach(row => println(row))

    val divBy2 = myRange.where("number % 2 == 0")

    divBy2.foreach(row => println(row))

  }

}
