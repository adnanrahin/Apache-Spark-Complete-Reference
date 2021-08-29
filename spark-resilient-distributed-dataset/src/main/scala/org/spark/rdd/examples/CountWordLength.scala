package org.spark.rdd.examples

object CountWordLength {

  def main(args: Array[String]): Unit = {

    val listOfString = List("Megatron", "Optimus", "Prime", "Bumble", "Cybertron")

    val lengths = listOfString.map(f => f.length)

    println(lengths)

    val sumOfLengths = lengths.reduceLeft(_ + _)

    println(sumOfLengths)

  }

}
