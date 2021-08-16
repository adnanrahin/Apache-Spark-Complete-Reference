package org.programming.scala.working_with_lists

object FilterInList {

  def main(args: Array[String]): Unit = {

    println(filterEvenNumbers(List(3, 4, 5, 6, 7, 8, 9, 90)))

  }

  def filterEvenNumbers(list: List[Int]): List[Int] = {
    def isEven(value: Int): Boolean = value % 2 == 0

    val newList = list.filter(isEven)
    newList
  }

}
