package org.programming.scala.working_with_lists

object FilterInList {

  def main(args: Array[String]): Unit = {

    println(filterEvenNumbers(List(3, 4, 5, 6, 7, 8, 9, 90)))

    val even, odd = listPartition(List(1, 2, 3, 4, 5, 6, 7, 8))

    println("Odd :" + odd)
    println("Even " + even)

  }

  def filterEvenNumbers(list: List[Int]): List[Int] = {
    def isEven(value: Int): Boolean = value % 2 == 0

    val newList = list.filter(isEven)
    newList
  }

  def listPartition(list: List[Int]): (List[Int], List[Int]) = {
    list.partition(_ % 2 == 0)
  }

}
