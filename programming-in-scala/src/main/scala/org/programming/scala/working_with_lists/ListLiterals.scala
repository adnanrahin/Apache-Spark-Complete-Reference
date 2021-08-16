package org.programming.scala.working_with_lists

object ListLiterals {

  def main(args: Array[String]): Unit = {
    
    val getString = appendListValues(List("Transformer", "Cybertruck", "Audi"))
    println(getString)
    val getPatternList = appendListValues(List(13, 4, 5, 56))
    println(getPatternList)

  }

  def appendListValues(value: Any): Any = {
    value match {
      case x: List[Any] => {
        x match {
          case f: List[String] => "String"
          case f: List[Int] => "Integer"
          case _ => "Desktop"
        }
      }
    }

  }

}
