package org.apache.spark.githubarcheive

import org.apache.spark.listhdfs.ListHdfsFiles
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

import scala.io.Source.fromFile

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

    /** actorLoginPushEven data set order by desc* */

    val actorLoginOrder = pushEvent
      .groupBy("actor.login")
      .count
      .orderBy(col("count").desc)

    println(actorLoginOrder.show(5))

    val employeesSet = Set() ++ (
      for {
        line <- fromFile("textdata/ghEmployees.txt").getLines
      } yield line.trim)

    val broadCastEmployeeSet = sc.broadcast(employeesSet)


    import spark.implicits._
    val isEmp = user => broadCastEmployeeSet.value.contains(user)
    val isEmployee = spark.udf.register("SetContainsUdf", isEmp)
    val filtered = actorLoginOrder.filter(isEmployee($"login"))
    println(filtered.show(5))

  }

}
