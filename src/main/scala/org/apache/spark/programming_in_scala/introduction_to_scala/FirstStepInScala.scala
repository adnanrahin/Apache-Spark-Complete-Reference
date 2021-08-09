package org.apache.spark.programming_in_scala.introduction_to_scala

import java.math.BigInteger

object FirstStepInScala {

  def main(args: Array[String]): Unit = {

    /** Partially applied function */

    val addNumbers = (x: Int, y: Int) => x + y
    val sum = addNumbers(2, 3)
    println(sum)

    /** Parameterized arrays with types */

    val big = new BigInteger("12345")

    val greetStrings = new Array[String](3)

    /**
     *
     * More explicit way to create greetStings is
     *
     * val greetStrings: Array[String] = new Array[String](3)
     *
     * */

    greetStrings(0) = "Hell0"
    greetStrings(1) = ", "
    greetStrings(2) = "World"

    for (i <- greetStrings.indices)
      println(greetStrings(i))

    /**
     * Alternative way to right above code
     *
     * val greetStrings = new Array[String](3)
     * greetStrings.update(0, "Hello")
     * greetStrings.update(1, ", ")
     * greetStrings.update(2, "world!\n")
     * for (i <- 0.to(2))
     * print(greetStrings.apply(i))
     * */

  }

}
