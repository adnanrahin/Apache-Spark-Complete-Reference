package org.programming.scala.case_classes_and_pattern_matching

object SortNDArrays {

  def main(args: Array[String]): Unit = {

    val sortedList = sortWithPatternMatching(Array(5, 6, 7, 3))
    println(sortedList)
    println(sortedList.getClass)

    val getSortedStringListByLength = sortWithPatternMatching(Array("Bad", "Book", "Megatron", "Optimus"))
    println(getSortedStringListByLength)
    println(getSortedStringListByLength.getClass)

    val getSortedStringToCharArray = sortWithPatternMatching("kbacdef")
    println(getSortedStringToCharArray)
  }

  def sortWithPatternMatching(anyArray: Any): Any = {

    anyArray match {
      case x: Array[Int] => {
        val sortedArray = x.sorted.toList
        sortedArray
      }
      case x: Array[String] => {
        val sortedString = x.toList.sortWith(_.length > _.length)
        sortedString
      }
      case x: String => {
        val charSortedArray = x.toCharArray.sorted.mkString(",")
        charSortedArray
      }
      case _ => println("whatever")
    }

  }

}
