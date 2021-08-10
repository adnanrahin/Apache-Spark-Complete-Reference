package org.apache.spark.spark_in_action.chapter_three_rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

import scala.io.Source.fromFile

object GitHubArchive {

  def main(args: Array[String]): Unit = {

    /**
     *
     *  args(0) -> hdfs://localhost:9000/datasource/*.json **/
     *  args(1) -> textdata\ghEmployees.txt
     *  args(2) -> git-out-put
     *
     * */

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("GitHubArchive")
      .getOrCreate()

    val sc = spark.sparkContext

    val gitHubLogsDf = spark.read.json(args(0))

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
        line <- fromFile(args(1)).getLines
      } yield line.trim)

    val broadCastEmployeeSet = sc.broadcast(employeesSet)

    import spark.implicits._
    val isEmp = user => broadCastEmployeeSet.value.contains(user)

    val sqlFunc = spark.udf.register("SetContainsUdf", isEmp)
    val filtered = actorLoginOrder.filter(sqlFunc($"login"))

    filtered.write.format(args(3)).save(args(2))
  }

}
