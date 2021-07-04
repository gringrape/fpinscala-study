import stream.Stream
import stream.Stream._

class Test extends org.scalatest.funsuite.AnyFunSuite {
  test("take") {
    assert(Stream(1, 2, 3, 4, 5).take(3).toList == List(1, 2, 3))
    assert(Stream(1, 2, 3, 4, 5).take(5).toList == List(1, 2, 3, 4, 5))
  }

  test("drop") {
    assert(Stream(1, 2, 3, 4, 5).drop(2).toList == List(3, 4, 5))
    assert(Stream(1, 2, 3, 4, 5).drop(6).toList == List())
  }

  test("takeWhile") {
    assert(
      Stream(1, 2, 3, 4, 5)
        .takeWhile(_ < 3)
        .toList == List(1, 2)
    )
  }

  test("constant") {
    assert(
      constant2(5).take(10).toList.sum == 50
    )
    assert(
      constant2(5).take(100).toList.sum == 500
    )
  }

  test("from") {
    assert(from(5).take(3).toList == List(5, 6, 7))
    assert(from(100).take(3).toList.sum == 303)
  }

  test("fibs") {
    assert(fibs().take(1).toList == List(0))
    assert(fibs().take(2).toList == List(0, 1))
    assert(fibs().take(5).toList == List(0, 1, 1, 2, 3))
  }

  // test("unfold - without Option") {
  //   assert(unfold(1)(_ + 1).take(3).toList == List(1, 2, 3))
  //   assert(unfold(1)(_ * 2).take(4).toList == List(1, 2, 4, 8))
  //   assert(unfold(3)(_ + 3).take(3).toList == List(3, 6, 9))
  // }

  test("unfold - with Option") {
    assert(
      unfold(10.0)((a: Double) => {
        if (a / 2 > 1) Some(a / 2)
        else None
      }).toList == List(10, 5, 2.5, 1.25)
    )
  }

  test("onesViaUnfold") {
    assert(onesViaUnfold.take(5).toList.sum == 5)
  }

  test("fromViaUnfold") {
    assert(fromViaUnfold(5).take(3).toList == List(5, 6, 7))
    assert(fromViaUnfold(5).take(5).toList.sum == 35)
  }

  test("fibsViaUnfold") {
    assert(fibsViaUnfold().take(5).toList == List(0, 1, 1, 2, 3))
  }
}
