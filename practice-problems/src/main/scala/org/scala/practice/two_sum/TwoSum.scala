package org.scala.practice.two_sum

object TwoSum {

  def main(args: Array[String]): Unit = {

    val nums: Array[Int] = Array(3, 4, 5, 6)

    val map: Map[Int, Int] = nums.zipWithIndex.toMap

    map.foreach(idx => println(idx._1 + " " + idx._2))

  }

  def twoSum(nums: Array[Int], target: Int): Array[Int] = {

    val zipIndex = nums.zipWithIndex

    val lookupMap = zipIndex.toMap

    zipIndex
      .collectFirst {
        case (value, index) if lookupMap.get(target - value).exists(_ != index) =>
          Array(index, lookupMap(target - value))
      }.getOrElse (Array())
  }

}
