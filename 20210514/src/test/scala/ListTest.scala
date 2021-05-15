import org.scalatest.funsuite.AnyFunSuite;

import fpinscala.datastructures
import fpinscala.datastructures.List
import fpinscala.datastructures.List._

class CubeCalculatorTest extends AnyFunSuite {
  // test("sum") {
  //   assert(sum(List(1, 2, 3)) === 6)
  // }

  // test("product") {
  //   assert(product(List(2, 5)) == 10)
  //   assert(product(List(2, 5, 5)) == 50)
  // }

  // test("foldRight") {
  //   assert(foldRight(List(1, 2, 3), 0, (a: Int, b: Int) => a + b) == 6)
  //   assert(foldRight(List(1, 2, 3, 4), 1, (a: Int, b: Int) => a * b) == 24)
  // }

  // test("sum2") {
  //   assert(sum2(List(1, 2, 3, 4)) == 10)
  // }

  test("product2") {
    assert(product2(List(2, 5, 0, 2)) == 0)
    //   assert(product2(List(2, 5, 5)) == 50)
  }

  // test("concat") {
  //   assert(concat(List("A", "PP", "L", "E")) == "APPLE")
  // }

  // test("length") {
  //   assert(length(List(1, 2, 3, 4)) == 4)
  // }
}
