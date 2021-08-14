package org.programming.scala.case_classes_and_pattern_matching

object RegexPatternMatching {

  def main(args: Array[String]): Unit = {

    println(regexPatternsMatching("120"))

  }

  def regexPatternsMatching(str: String): String = {

    val numeric = """([0-9]+)""".r
    val alphabetic = """([a-zA-Z]+)""".r
    val alphanumeric = """([a-zA-Z0-9]+)""".r

    str match {
      case numeric(value) => s"I'm a numeric with value $value"
      case alphabetic(value) => s"I'm an alphabetic with value $value"
      case alphanumeric(value) => s"I'm an alphanumeric with value $value"
      case _ => s"I contain other characters than alphanumerics. My value $str"
    }

  }

}
