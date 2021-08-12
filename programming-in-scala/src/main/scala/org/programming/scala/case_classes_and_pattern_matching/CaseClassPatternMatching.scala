package org.programming.scala.case_classes_and_pattern_matching

object CaseClassPatternMatching {

  abstract class Expr

  case class Var(name: String) extends Expr

  case class Number(num: Double) extends Expr

  case class UnOp(operator: String, arg: Expr) extends Expr

  case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

  def main(args: Array[String]): Unit = {

    val v = Var("X")

    println(v.name)

    val op = BinOp(" + ", Number(1), v)

    println(op)

  }

}
