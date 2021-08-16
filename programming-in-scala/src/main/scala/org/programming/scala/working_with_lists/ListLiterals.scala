package org.programming.scala.working_with_lists

object ListLiterals {

  def main(args: Array[String]): Unit = {

    println(appendListValues(List("Transformer", "Cybertruck", "Audi")))
    println(appendListValues(List(1, 2, 3)))

  }

  def appendListValues(list: List[Any]): List[Any] = {

    list match {
      case x: List[String] => {
        val newList = x.map(str => str + "a")
        newList
      }
      case x: List[Integer] => {
        val newList = x.map(value => value + 1)
        newList
      }
      case _ => Nil
    }
  }

}
