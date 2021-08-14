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

    val getSortedStringList = sortWithPatternMatching(List("Mechanics", "Prime", "Number", "Pattern", "Romeo"))
    println(getSortedStringList)
    println(getSortedStringList.getClass)

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
        val charSortedArray = x.toCharArray.sorted.toList
        charSortedArray
      }

      case x: List[String] => {
        val sortedStringList = x.sorted
        sortedStringList
      }

      case _ => println("whatever")
    }

  }

}
