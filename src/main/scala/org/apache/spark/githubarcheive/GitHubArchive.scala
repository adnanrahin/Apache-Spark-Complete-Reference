package org.apache.spark.githubarcheive

import org.apache.spark.listhdfs.ListHdfsFiles
import org.apache.spark.sql.SparkSession

object GitHubArchive {

  def main(args: Array[String]): Unit = {

    val fileLists = new ListHdfsFiles()

    val listOfFiles = fileLists.listHdfsFiles("datasource")

    listOfFiles.foreach(file => println(file.getPath))

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("GitHubArchive")
      .getOrCreate()

    val sc = spark.sparkContext

    val gitHubLogsDf = spark.read.json("hdfs://localhost:9000/datasource/2015-03-01-0.json")

    val pushEvent = gitHubLogsDf.filter("type = 'PushEvent'")

    println(pushEvent.show(30))

    /** Group pushEvent dataset by actor.login* */

    val actorLoginPushEvent = pushEvent.groupBy("actor.login").count()

    println(actorLoginPushEvent.show(5))

  }

}
