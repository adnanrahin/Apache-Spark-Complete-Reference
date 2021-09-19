package org.spark.apis.readfromcluster

import org.apache.spark.sql.SparkSession

object ReadFileFromHdfsClusterToDF {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("ReadFileFromHdfsCluster")
      .getOrCreate()

    val df = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("hdfs://localhost:9000/data/retail-data/by-day/2010-12-01.csv")

    println(df.show(20))

  }

}
