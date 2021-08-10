package org.programming.scala.clsss_and_objects

import scala.collection.mutable

class ChecksumAccumulator {

  private var sum = 0

  def add(b: Byte): Unit = {
    sum += b
  }

  def checkSum(): Int = {
    ~(sum & 0xFF) + 1
  }

  object ChecksumAccumulator {
    private val cache = mutable.Map[String, Int]()

    def calculate(s: String): Int =
      if (cache.contains(s))
        cache(s)
      else {
        val acc = new ChecksumAccumulator
        for (c <- s)
          acc.add(c.toByte)
        val cs = acc.checkSum()
        cache += (s -> cs)
        cs
      }
  }
}
