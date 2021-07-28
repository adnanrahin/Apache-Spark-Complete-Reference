package org.apache.spark.samplescala

import scala.io.Source.fromFile

object TrimGithubEmployees {

  def main(args: Array[String]): Unit = {

    val ghPath = "src/main/scala/org/apache/spark/samplescala/ghEmployees.txt"

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

  }

}
