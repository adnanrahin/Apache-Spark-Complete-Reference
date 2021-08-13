package org.programming.scala.case_classes_and_pattern_matching

object ConstantPatternMatching {

  def main(args: Array[String]): Unit = {

    val one = constantsPatternMatching("One")
    println(one)

    val two = constantsPatternMatching("two")
    println(two)

  }

  def constantsPatternMatching(constant: String): Int = {
    constant match {
      case "One" | "one"| "ONE" => 1
      case "Two" | "TWO" | "two" => 2
      case "Three" => 3
      case _ => -1
    }
  }

}
