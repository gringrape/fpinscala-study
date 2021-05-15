import org.scalatest.funsuite.AnyFunSuite;

import test.Stream;
import test.Stream._;

class StreamTest extends AnyFunSuite {
  // test("toList") {
  //   assert(Stream(1, 2, 3).toList == List(1, 2, 3))
  // }

  // test("take") {
  //   assert(Stream(1, 2, 3, 4, 5).take(3) == Stream(1, 2, 3))
  // }

  test("equals") {
    assert(Stream(1, 2, 3) == Stream(1, 2, 3))
  }
}
