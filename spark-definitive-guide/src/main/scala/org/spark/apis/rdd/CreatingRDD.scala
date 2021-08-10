package org.spark.apis.rdd

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object CreatingRDD {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("CreatingRDD")
      .getOrCreate()

    val rdd = spark.range(500).rdd

    //rdd.foreach(row => println(row))

    /*parallelize method on a SparkContext (within a SparkSession). */

    val myCollection = "Spark The Definitive Guide : Big Data Processing Made Simple"
      .split(" ")

    val words = spark.sparkContext.parallelize(myCollection, 2)

    words.foreach(word => println(word))

    println(words.distinct().count())

    println("Filter words that start with S")

    val startWithS = words.filter(word => startsWithS(word)).collect()

    startWithS.foreach(word => println(word))

    println("Filter words using Map Function")

    val words2 = words.map(word => (word, word(0), word.startsWith("S")))

    words2.foreach(word => println(word))

    println("Flat Map")

    val flatWord = words.flatMap(word => word.toSeq).take(23)

    println(flatWord.mkString("Array(", ", ", ")"))


    /* Create new RDD using map function*/
    val wordsKeyVal = words.map(word => (word.toLowerCase, 1, word.length))

    wordsKeyVal.foreach(row => println(row))

    val keyword = words.keyBy(word => word.toLowerCase.toSeq.head.toString)

    keyword.foreach(word => println(word))

    println("Keyword maps values to Upper Case")

    val toUpperCaseMapValue = keyword.mapValues(word => word.toUpperCase).collect()

    toUpperCaseMapValue.foreach(println)

    println("Flat Map Words")

    val keyValuesToFlatMap = keyword.flatMapValues(word => word.toUpperCase).collect()

    println(keyValuesToFlatMap.mkString("Array(", ", ", ")"))

    /*
    * Get all distinct Characters
    * */

    val distinctChars = words.flatMap(word => word.toLowerCase.toSeq).distinct.collect()

    println(distinctChars.mkString("Array(", ", ", ")"))

    val chars = words.flatMap(word => word.toLowerCase.toSeq)

    chars.foreach(c => print(c + ", "))

    /** Key values RDD Creating **/

    val kvCharacters = chars.map(letter => (letter, 1))

    kvCharacters.foreach(k => println(k._1 + " " + k._2))

    def maxFunc(left: Int, right: Int): Int = math.max(left, right)

    def addFunc(left: Int, right: Int): Int = left + right

    val numSum = addFunc(4, 5)

    println(numSum)

    /** RDD to find all the characters frequency in key-value pair characters map**/

    println("LETTER FREQUENCY IN KEYS")

    val letterFrequencyMap = kvCharacters.groupByKey().map(k => (k._1, k._2.reduce(addFunc))).collect()

    letterFrequencyMap.foreach(key => println(key._1 + " " + key._2))

    /** foldByKey function**/

    val foldByKey = kvCharacters.foldByKey(0)(addFunc).collect()

    println("FOLD BY KEY")

    foldByKey.foreach(row => println(row))

  }

  def startsWithS(word: String) = {
    word.startsWith("S")
  }

}
