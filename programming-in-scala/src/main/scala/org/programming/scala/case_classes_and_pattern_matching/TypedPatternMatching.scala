package org.programming.scala.case_classes_and_pattern_matching

object TypedPatternMatching {

  def main(args: Array[String]): Unit = {

    val getPatternValue = typedPatternMatching("My Name is Megatron")
    println(getPatternValue)

    val getPatternList = typedPatternMatching(List(1, 3, 4, 5, 5))
    println(getPatternList)
    
  }

  def typedPatternMatching(value: Any): String = {
    value match {
      case x: String => s"$value is String"
      case x: Int => s"$value is Integer"
      case x: List[Int] => s"$x is a List Integer"
      case x: List[String] => s"$x is List String"
      case _ => s"No one knows what $value is"
    }

  }

}
