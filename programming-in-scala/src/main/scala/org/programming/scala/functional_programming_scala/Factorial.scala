package org.programming.scala.functional_programming_scala

object Factorial {

  def main(args: Array[String]): Unit = {

    println(factorial(10))

  }

  def factorial(i: Int): Long = {
    def fact(i: Int, accumulator: Long): Long = {
      if (i <= 1) accumulator
      else fact(i - 1, i * accumulator)
    }

    fact(i, 1)
  }

}
