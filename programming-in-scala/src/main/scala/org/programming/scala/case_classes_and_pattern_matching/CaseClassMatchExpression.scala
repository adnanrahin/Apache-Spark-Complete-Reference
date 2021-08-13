package org.programming.scala.case_classes_and_pattern_matching

object CaseClassMatchExpression {

  abstract class Animal

  case class Mammal(name: String, fromSea: Boolean) extends Animal

  case class Bird(name: String) extends Animal

  case class Fish(name: String) extends Animal

  case class Bug(name: String) extends Animal

  def main(args: Array[String]): Unit = {

    val mammal =  Mammal("Shark", fromSea = true)

    val getDescription = caseClassPatternMatchExpression(mammal)

    println(getDescription)

    val bird = Bird("Humming Bird")
    println(caseClassPatternMatchExpression(bird))

    val bug = Bug("Butterfly")
    println(caseClassPatternMatchExpression(bug))
  }

  def caseClassPatternMatchExpression(animal: Animal): String = {
    animal match {
      case Mammal(name, fromSea) => s"Mammal name is $name, is it from sea $fromSea"
      case Bird(name) => s"Bird Name is $name"
      case Fish(name) => s"Fish Name is $name"
      case _ => "This animal does not match anything"
    }
  }

}
