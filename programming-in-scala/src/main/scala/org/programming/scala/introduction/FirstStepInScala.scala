package org.programming.scala.introduction

import java.math.BigInteger
import scala.collection.mutable

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

    /** Scala List */

    val onwTwo = List(1, 2)
    val threeFour = List(3, 4)
    val addLists = onwTwo ::: threeFour
    println(addLists)

    val addElemToList = 10 :: threeFour
    println(addElemToList)

    val anotherWay = 1 :: 2 :: 3 :: Nil
    println(anotherWay)

    /** Tuples in Scala */

    val pair = (1000, "Megatron")
    println(pair._1 + " " + pair._2)

    /** Scala Set */

    var jetSet = Set("Boeing", "Airbus")
    jetSet += "Lear"
    println(jetSet.contains("Cessna") + " " + jetSet.contains("Boeing"))
    println(jetSet.getClass)

    /** Scala Mutable Map */
    val treasureMap = mutable.Map[Int, String]()
    treasureMap += (1 -> "Go to island.")
    treasureMap += (2 -> "Find big X on ground.")
    treasureMap += (3 -> "Dig.")
    println(treasureMap(2))
    println(treasureMap.getClass)

    /** Scala Immutable Map */
    val romanNumeral = Map(
      1 -> "I", 2 -> "II", 3 -> "III", 4 -> "IV", 5 -> "V"
    )
    println(romanNumeral(4))
    println(romanNumeral.getClass)


  }

}
