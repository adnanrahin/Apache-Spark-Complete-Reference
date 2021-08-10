package org.programming.scala.introduction

import scala.io.Source

object ReadFileOne {

  def main(args: Array[String]): Unit = {

    // readFileFromSource(args(0))

    // readLinesFromSourceAddToList(args(0))

    findLongestLineInTheSourceFile(args(0))

  }

  def readLinesFromSource(filePath: String): Unit = {
    if (filePath.nonEmpty) {
      for (line <- Source.fromFile(filePath).getLines())
        println(line.length + " -> " + line)
    } else Console.err.println("File Not Found")
  }

  def readLinesFromSourceAddToList(filePath: String): List[String] = {

    val lines = Source.fromFile(filePath).getLines().toList

    lines.foreach(line => println(line))

    lines
  }

  def findLongestLineInTheSourceFile(filePath: String): Unit = {

    def widthOfLength(str: String) = str.length.toString.length

    if (filePath.nonEmpty) {

      val lines = Source.fromFile(filePath).getLines.toList

      val longestLine = lines.reduceLeft(
        (a, b) => if (a.length > b.length) a else b
      )

      val maxWidth = widthOfLength(longestLine)

      for (line <- lines) {
        val numSpaces = maxWidth - widthOfLength(line)
        val padding = " " * numSpaces
        print(padding + line.length + " | " + line)
      }

    }
    else
      Console.err.println("Please enter filename")

  }

}
