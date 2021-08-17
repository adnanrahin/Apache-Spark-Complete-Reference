package org.programming.scala.working_with_lists

object FilterInList {

  def main(args: Array[String]): Unit = {

    println(filterEvenNumbers(List(3, 4, 5, 6, 7, 8, 9, 90)))

    val even, odd = listPartition(List(1, 2, 3, 4, 5, 6, 7, 8))

    println("Odd :" + odd)
    println("Even " + even)

    println(reduceFromLeft(List(3, 4, 56, 8)))

    println(reduceString(List("ami", "b", "cb", "alpha")))

  }

  def filterEvenNumbers(list: List[Int]): List[Int] = {
    def isEven(value: Int): Boolean = value % 2 == 0

    val newList = list.filter(isEven)
    newList
  }

  def listPartition(list: List[Int]): (List[Int], List[Int]) = {
    list.partition(_ % 2 == 0)
  }

  def reduceFromLeft(list: List[Int]): Int = {

    def findMaxValue(a: Int, b: Int): Int = if (a > b) a else b

    list.reduceLeft(findMaxValue)

  }

  def reduceString(strs: List[String]): List[String] = {

    strs.scanLeft("")((x, y) => if(x.startsWith("a") && y.endsWith("b")) (x + y) else (x + "  |  " + y))

  }

}
