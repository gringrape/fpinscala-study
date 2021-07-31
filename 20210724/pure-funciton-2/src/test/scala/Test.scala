import org.scalatest.funsuite.AnyFunSuite

import simplerng.RNG.SimpleRNG

import simplerng.RNG.intDouble
import simplerng.RNG.ints

class Test extends AnyFunSuite {
  val preRNG = SimpleRNG(42)

  test("indDouble") {
    val largeNumber = "197491923327988"

    val newRNG = SimpleRNG(largeNumber.toLong)

    assert(intDouble(preRNG) == (
      (16159453, 0.5967354861072895), newRNG
    ))
  }

  test("ints") {
    val largeNumber = "259172689157871"

    assert(ints(3)(preRNG) == (
      List(16159453, -1281479697, -340305902),
      SimpleRNG(largeNumber.toLong)
    ))
  }
}
