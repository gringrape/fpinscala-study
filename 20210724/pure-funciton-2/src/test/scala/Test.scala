import org.scalatest.funsuite.AnyFunSuite

import simplerng.RNG.SimpleRNG

import simplerng.RNG._

class Test extends AnyFunSuite {
  val preRNG = SimpleRNG(42)

  test("indDouble") {
    val largeNumber = "197491923327988"

    val newRNG = SimpleRNG(largeNumber.toLong)

    assert(
      intDouble(preRNG) == (
        (16159453, 0.5967354861072895), newRNG
      )
    )
  }

  test("ints") {
    val largeNumber = "259172689157871"

    assert(
      ints(3)(preRNG) == (
        List(16159453, -1281479697, -340305902),
        SimpleRNG(largeNumber.toLong)
      )
    )
  }

  test("unit") {
    assert(unit(1)(SimpleRNG(1)) == (1, SimpleRNG(1)))
  }

  test("map") {
    // 상태전이 + '출력값' 변형.
    assert(map(unit(1))(_ + 3)(SimpleRNG(1)) == (4, SimpleRNG(1)))
  }

  test("_double") {
    val largeNumber = "25214903928"

    assert(
      _double(SimpleRNG(1)) == (1.79162249052507e-4, SimpleRNG(
        largeNumber.toLong
      ))
    )
  }

  test("map2") {
    val rand1: Rand[Int] = nonNegativeInt
    val rand2 = _double
    assert(map2(rand1, rand2)(_ + _) == ((1, 0.1), SimpleRNG(1)))
  }
}
