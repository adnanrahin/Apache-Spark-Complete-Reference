package org.programming.scala.functional_objects

object FunctionalObjects {

  class Rational(n: Int, d: Int) {

    require(d != 0)

    val numer: Int = n
    val denom: Int = d

    override def toString: String = n + "/" + d

    def add(that: Rational): Rational =
      new Rational(
        numer * that.denom + that.numer * denom,
        denom * that.denom
      )
  }

  def main(args: Array[String]): Unit = {

    val oneHalf = new Rational(1, 2)

    println(oneHalf)

  }

}
