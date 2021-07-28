package org.apache.spark.githubarcheive

import org.apache.spark.sql.SparkSession

object GitHubArchive {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("GitHubArchive")
      .getOrCreate()

  }

}
