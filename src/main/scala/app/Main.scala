package app

import main.scala.solvers.{FunctionalLinearImmutable, ImperativeLinear, Quadratic}
import main.scala.utils.Utils

object Main {

  def main(args:Array[String]):Unit = {
    val (qua, fun, imp) = args.headOption.getOrElse("all") match {
      case "all" => (true, true, true)
      case "quadratic" => (true, false, false)
      case "functional" => (false, true, false)
      case "imperative" => (false, false, true)
      case other =>
        println("use one of: all, quadratic, functional, imperative")
        return;
    }

    val maxPalindromes = 1000000000

    var distinctChars = 20
    var sentenceLength = 100000
    var sentence = Utils.randomSequence(distinctChars, sentenceLength)

    var quadraticTime = 0.0
    var functionalLinearImmutableTime = 0.0
    var imperativeLinearTime = 0.0

    if (qua) quadraticTime = Utils.time (new Quadratic(sentence, maxPalindromes))
    if (fun) functionalLinearImmutableTime = Utils.time (new FunctionalLinearImmutable(sentence, maxPalindromes))
    if (imp) imperativeLinearTime = Utils.time (new ImperativeLinear(sentence, maxPalindromes))

    println("Quadratic solver takes " + quadraticTime + " ms to finish for random sentence of length " + sentenceLength + " composed of " + distinctChars  +" distinct chars")
    println("Functional Linear solver takes " + functionalLinearImmutableTime + " ms to finish for random sentence of length " + sentenceLength + " composed of " + distinctChars  +" distinct chars")
    println("Imperative linear solver takes " + imperativeLinearTime + " ms to finish for random sentence of length " + sentenceLength + " composed of " + distinctChars  +" distinct chars")

    println()

    if (qua) quadraticTime = Utils.time (new Quadratic("a"*1000 + "b" * 1000 + "c" * 1000, maxPalindromes))
    if (fun) functionalLinearImmutableTime = Utils.time (new FunctionalLinearImmutable("a"*1000 + "b" * 1000 + "c" * 1000, maxPalindromes))
    if (imp) imperativeLinearTime = Utils.time (new ImperativeLinear("a"*1000 + "b" * 1000 + "c" * 1000, maxPalindromes))

    println("Quadratic solver takes " + quadraticTime + " ms to finish for sentence a^1000 + b^1000 + c^1000")
    println("Functional Linear solver takes " + functionalLinearImmutableTime + " ms to finish for sentence a^1000 + b^1000 + c^1000")
    println("Imperative linear solver takes " + imperativeLinearTime +  " ms to finish for sentence a^1000 + b^1000 + c^1000")

    println()

    distinctChars = 3
    sentenceLength = 500000
    sentence = Utils.randomSequence(distinctChars, sentenceLength)

    if (qua) quadraticTime = Utils.time (new Quadratic(sentence, maxPalindromes))
    if (fun) functionalLinearImmutableTime = Utils.time (new FunctionalLinearImmutable(sentence, maxPalindromes))
    if (imp) imperativeLinearTime = Utils.time (new ImperativeLinear(sentence, maxPalindromes))

    println("Quadratic solver takes " + quadraticTime + " ms to finish for random sentence of length " + sentenceLength + " composed of " + distinctChars  +" distinct chars")
    println("Functional Linear solver takes " + functionalLinearImmutableTime + " ms to finish for random sentence of length " + sentenceLength + " composed of " + distinctChars  +" distinct chars")
    println("Imperative linear solver takes " + imperativeLinearTime + " ms to finish for random sentence of length " + sentenceLength + " composed of " + distinctChars  +" distinct chars")
  }
}
