package org.programming.scala.working_with_lists

object HigherOrderMethod {

  def main(args: Array[String]): Unit = {

    println(addOne(List(1, 2, 3, 4, 5, 6)))
    println(getLengthOfAllStrings(List("megatron", "time", "travel")))
    println(reverseStringsInList(List("megatron", "time", "travel")))
  }

  def addOne(list: List[Int]): List[Int] = {
    val newList = list.map(_ + 1)
    newList
  }

  def getLengthOfAllStrings(strs: List[String]): List[Int] = {
    val newList = strs.map(_.length)
    newList
  }

  def reverseStringsInList(list: List[String]): List[String] = {
    list.map(_.reverse)
  }

}
