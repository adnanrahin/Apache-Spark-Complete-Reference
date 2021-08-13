package org.programming.scala.case_classes_and_pattern_matching

object SortNDArrays {

  def main(args: Array[String]): Unit = {

    val sortedList = sortWithPatternMatching(Array(5, 6, 7, 3))

    println(sortedList)
  }

  def sortWithPatternMatching(anyArray: Array[Integer]): Any = {

    anyArray match {
      case Array(_*) => {
        val arrToList = anyArray.toList.sorted
        arrToList
      }
    }

  }

}
