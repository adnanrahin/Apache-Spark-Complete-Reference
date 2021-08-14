package org.programming.scala.case_classes_and_pattern_matching

object PatternGuardsMatching {

  def main(args: Array[String]): Unit = {

  }

  def patternGuards(toMatch: Any, maxLength: Int): String = {
    toMatch match {
      case list: List[Any] if (list.size <= maxLength) => "List is of acceptable size"
      case list: List[Any] => "List has not an acceptable size"
      case string: String if (string.length <= maxLength) => "String is of acceptable size"
      case string: String => "String has not an acceptable size"
      case _ => "Input is neither a List nor a String"
    }
  }

}
