package org.apache.spark.samplescala

import org.apache.spark.sql.SparkSession

import scala.io.Source.fromFile

object TrimGithubEmployees {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("GitHubArchive")
      .getOrCreate()

    val ghPath = "textdata/ghEmployees.txt"

    val employees = Set() ++ (
      for {
        line <- fromFile(ghPath).getLines
      } yield line.trim
      )

    println(employees.getClass)

    val filetEmployees =
      employees.filter(name => name.startsWith("a"))

    println(filetEmployees.toString())
    println(filetEmployees.getClass)

    val isEmp: (String => Boolean) = (arg: String) => employees.contains(arg)

    val isEmployee = spark.udf.register("isEmpUdf", isEmp)

    println(isEmployee)

    val isIn = isEmp("add")

    println(isIn)

  }

}
