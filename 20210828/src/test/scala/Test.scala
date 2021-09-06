import simplerng.RNG._

class Test extends org.scalatest.funsuite.AnyFunSuite {
  test("works") {
    assert(1 + 1 === 2)
  }

  test("non-negative-less-than via flatMap") {
    val rng = SimpleRNG(10)

    val (nextVal, nextRng) = nonNegativeLessThan(15)(rng)
    assert(nextVal < 15)
  }
}
