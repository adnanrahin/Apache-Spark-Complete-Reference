package org.programming.scala.control_abstraction

import java.io.File

object ControlAbstraction {

  def main(args: Array[String]): Unit = {

    val files = filesHere

    println(files.mkString("Array(", ", ", ")"))

  }

  private def filesHere = new File(".").listFiles

}
