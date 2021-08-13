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
    val op = BinOp(" + ", Number(2), v)
    println(op)
    val a = simplifyTop(op)
    println(a)

    val five = describe(5)
    println(five)

    val hello = describe("hello")
    println(hello)

    val trueVal = describe(false)
    println(trueVal)

    val getNullPointerException = describe(Nil)
    println(getNullPointerException)
  }

  def simplifyTop(expr: Expr): Expr = expr match {
    case UnOp("-", UnOp("-", e)) => e
    case BinOp("+", e, Number(0)) => e
    case BinOp("*", e, Number(1)) => e
    case _ => expr
  }

  def describe(x: Any): Any = x match{
    case 5 => "Five"
    case true => true
    case "Hello" => "Hello String"
    case Nil => "Null"
    case _ => "Something Else"
  }

}
