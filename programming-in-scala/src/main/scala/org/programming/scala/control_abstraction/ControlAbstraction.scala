package org.programming.scala.control_abstraction

import java.io.File

object ControlAbstraction {

  def main(args: Array[String]): Unit = {

    val files = filesHere

    println(files.mkString("Array(", ", ", ")"))

    println(findFilesByEndNames("git", files).mkString("Array(", ", ", ")"))

  }

  private def filesHere = new File(".").listFiles

  def findFilesByEndNames(query: String, files: Array[File]): Array[File] = {
    for {
      file <- files
      if (file.getName.contains(query))
    } yield file
  }

}
