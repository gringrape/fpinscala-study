import org.scalatest.funsuite.AnyFunSuite
import simplerng.RNG.SimpleRNG
import simplerng.RNG.nonNegativeInt
import simplerng.RNG.double

class Test extends AnyFunSuite {
  test("test works") {
    assert(1 + 1 === 2)
  }

  test("creation") {
    SimpleRNG(3)
  }

  test("nextInt returns number and generator") {
    val rng = SimpleRNG(42)
    val number = "1059025964525".toLong
    assert(rng.nextInt == (16159453, SimpleRNG(number)))
  }

  test("with the same seed") {
    val rng = SimpleRNG(42)
    val number: String = "1059025964525"
    assert(rng.nextInt == (16159453, SimpleRNG(number.toLong)))
  }

  test("with another seed") {
    val rng = SimpleRNG(41)
    val number: String = "1059025964525"
    assert(rng.nextInt != (16159453, SimpleRNG(number.toLong)))
  }

  test("nonNegativeInt") {
    val number: String = "1059025964525"
    val rng = SimpleRNG(number.toLong)
    val (n, nextRng) = nonNegativeInt(rng)

    assert(n > 0)
  }

  test("double") {
    val number: String = "1059025964525"
    val rng = SimpleRNG(number.toLong)
    val (n, nextRNG) = double(rng)

    assert(n === 0.5967354861072895)
  }

  test("double with many seeds") {
    val seeds = List.range(5000, 10000)

    assert(seeds.forall((seed) => {
      val rng = SimpleRNG(seed)
      val (n, nextRNG) = double(rng)

      println(n)

      0 <= n && n < 1
    }))
  }
}
