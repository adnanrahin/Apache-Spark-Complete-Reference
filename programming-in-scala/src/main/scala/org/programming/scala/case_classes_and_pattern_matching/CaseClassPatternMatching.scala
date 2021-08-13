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

    val hello = describe("Hello")
    println(hello.toString.substring(0, 5))

    val trueVal = describe(false)
    if (trueVal == true) {
      println(trueVal)
    } else println(trueVal + " Became False")

    val getNullPointerException = describe(Nil)
    println(getNullPointerException)
  }

  def simplifyTop(expr: Expr): Expr = expr match {
    case UnOp("-", UnOp("-", e)) => e
    case BinOp("+", e, Number(0)) => e
    case BinOp("*", e, Number(1)) => e
    case _ => expr
  }

  def describe(x: Any): Any = x match {
    case 5 => "Five"
    case true => true
    case "Hello" => "Hello String"
    case Nil => "Null"
    case _ => "Something Else"
  }

  def generalizedSize(x: Any): Int = x match {
    case s: String => s.length
    case m: Map[_, _] => m.size
  }

  def regexPatterns(toMatch: String): String = {
    val numeric = """([0-9]+)""".r
    val alphabetic = """([a-zA-Z]+)""".r
    val alphanumeric = """([a-zA-Z0-9]+)""".r

    toMatch match {
      case numeric(value) => s"I'm a numeric with value $value"
      case alphabetic(value) => s"I'm an alphabetic with value $value"
      case alphanumeric(value) => s"I'm an alphanumeric with value $value"
      case _ => s"I contain other characters than alphanumerics. My value $toMatch"
    }
  }

  def patternMatching(candidate: String): Int = {
    candidate match {
      case "One" => 1
      case "Two" => 2
      case _ => -1
    }
  }

}
