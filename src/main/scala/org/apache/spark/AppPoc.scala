package org.apache.spark

object AppPoc {

  def main(args: Array[String]): Unit = {
    println("Hello from App")
    val conf = new SparkConf()
      .setAppName("Testing POC Intellij Maven")
      .setMaster("local[*]")

    val sc = new SparkContext(conf)

    val col = sc.parallelize(5 to 200 by 5)
    val smp = col.sample(withReplacement = true, 5)
    val colCount = col.count
    val smpCount = smp.count

    println("original number = " + colCount)
    println("sampled number = " + smpCount)
    println("Bye from this App")
  }
}
