import org.scalatest.funsuite.AnyFunSuite

import lazyStructures.Stream
import lazyStructures.Empty
import lazyStructures.Cons

class StreamTest extends AnyFunSuite {
  def sum(as: Int*) = 15

  test("creation") {
    Stream()
  }

  test("creation with integers") {
    Stream(1)
    Stream(1, 2, 3)
    Stream('1', 'a', 'b')
  }

  test("apply") {
    assert(Stream(1, 2) == Cons(() => 1, () => Cons(() => 2, () => Empty)))
  }

  test("가변인자 시퀀스 테스트 -> 가변인자를 인수로 받는 함수에 Seq 를 인수로 주는 경우") {
    assert(sum(Seq(1, 2, 3): _*) == 6)
    assert(sum(List(1, 2, 3, 4, 5): _*) == 15)
  }
}
