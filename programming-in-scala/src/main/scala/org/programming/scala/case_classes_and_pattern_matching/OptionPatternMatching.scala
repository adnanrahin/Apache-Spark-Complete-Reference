package org.programming.scala.case_classes_and_pattern_matching

object OptionPatternMatching {

  def main(args: Array[String]): Unit = {

    val stringMap = Map("1" -> List("eat", "tea", "tan", "ate", "nat", "bat"))

    println(optionPatternMatching(stringMap.get("1")))
    println(optionPatternMatching(stringMap.get("0")))

  }

  def optionPatternMatching(value: Option[Any]): Any = {
    value match {
      case Some(x) => {
        x match {
          case x: Int => s"$x is Integer."
          case x: String => s"$x is String."
          case x: List[String] => {
            val anagrams = x.groupBy(_.sorted).map(f => f._2.toList).toList
            anagrams
          }
        }
      }
      case None => s"$value is None or Nil"
    }
  }

}
