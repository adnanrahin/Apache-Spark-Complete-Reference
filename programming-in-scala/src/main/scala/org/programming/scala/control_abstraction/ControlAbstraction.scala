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
      if (file.getName.endsWith(query))
    } yield file
  }

  def findFilesByMatch(query: String, files: Array[File]): Array[File] = {
    for {
      file <- files
      if (file.getName.matches(query))
    } yield file
  }

  def filesMatching(query: String, matcher: (String, String) => Boolean): Array[File] = {
    for (file <- filesHere; if matcher(file.getName, query))
      yield file
  }

  def filesEnding(query: String): Array[File] = filesMatching(query, _.endsWith(_))

}
