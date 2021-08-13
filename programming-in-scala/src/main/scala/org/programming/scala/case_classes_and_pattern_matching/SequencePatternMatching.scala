package org.programming.scala.case_classes_and_pattern_matching

object SequencePatternMatching {

  def main(args: Array[String]): Unit = {

    println(sequencesPatternMatching(List(1)))
    println(sequencesPatternMatching(List(1, 2, 3, 4)))
    println(sequencesPatternMatching(Vector(1, 2, 5, 6)))
    println(sequencesPatternMatching(Vector(5, 6)))
    println(sequencesPatternMatching(Set(4, 5, 6)))
  }

  def sequencesPatternMatching(sequence: Any): String = {

    sequence match {
      case List(singleElement) => s"List Contain Single Element $singleElement"
      case List(_, _*) => s"List Contain Multiple Elements $sequence"
      case Vector(1, 2, _*) => s"Sequence is Vector $sequence"
      case Vector(_*) => s"Sequence is Vector, Contains Multiple Values, $sequence"
      case _ => s"Unrecognized sequence. My value: $sequence"
    }

  }

}
