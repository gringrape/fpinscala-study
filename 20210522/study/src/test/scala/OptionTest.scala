import org.scalatest.funsuite.AnyFunSuite

import exception.Option.mean
import exception._

class OptionTest extends AnyFunSuite {
  test("mean") {
    assert(mean(List(1, 2, 3)) == Some(2.0))
    assert(mean(List(2, 4, 6)) == Some(4.0))
  }

  test("mean with empty list") {
    assert(mean(List()) == None)
  }

  test("map") {
    assert(Some(3).map(_ + 1) == Some(4))
  }

  test("getOrElse") {
    assert(Some(3).getOrElse(2) == 3)
  }

  test("getOrElse when Option is None") {
    assert(None.getOrElse(2) == 2)
  }

  test("filter - condition match") {
    assert(Some(3).filter(_ % 2 == 1) == Some(3))
  }

  test("filter - condition doesn`t match") {
    assert(Some(2).filter(_ % 3 == 1) == None)
  }

  // test("when Option is None, map returns None") {
  //   assert(None.map(a => a + 1) == None)
  // }
}
