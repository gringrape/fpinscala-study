import org.scalatest.funsuite.AnyFunSuite

import stream.Stream
import stream.Stream.from
import stream.Stream.fibs

class Test extends AnyFunSuite {
  test("toList") {
    assert(Stream(1, 2, 3).toList == List(1, 2, 3))
    assert(Stream(1, 2, 3, 4).toList == List(1, 2, 3, 4))
  }

  test("exists") {
    assert(Stream(1, 3, 2, 5).exists(_ % 2 == 0) == true)
  }

  test("foldRight") {
    assert(Stream(1, 2, 3).foldRight(0)(_ + _) == 6)
    assert(Stream(1, 2, 3, 4, 5).foldRight(1)(_ * _) == 120)
  }

  test("exists2") {
    assert(Stream(1, 2, 3, 4, 5).exists2(_ > 2) == true)
  }

  test("forAll") {
    assert(Stream(1, 2, 3, 4).forAll(_ < 3) == false)
    assert(Stream(1, 2, 3, 4, 5).forAll(_ < 6) == true)
  }

  test("take") {
    assert(Stream(1, 2, 3, 4).take(2).toList == List(1, 2))
  }

  test("from") {
    assert(from(1).take(5).toList == List(1, 2, 3, 4, 5))
  }

  test("fibs") {
    assert(fibs().take(1).toList == List(0))
    assert(fibs().take(2).toList == List(0, 1))
    assert(fibs().take(5).toList == List(0, 1, 1, 2, 3))
  }
}
