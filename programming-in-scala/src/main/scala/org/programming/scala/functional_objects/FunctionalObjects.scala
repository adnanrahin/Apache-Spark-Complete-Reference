package org.programming.scala.functional_objects

object FunctionalObjects {

  class Rational(n: Int, d: Int){

    /*Override twString method*/

    override def toString: String = n + "/" + d

  }
 
  def main(args: Array[String]): Unit = {

    val oneHalf = new Rational(1, 2)

    println(oneHalf)

  }

}
