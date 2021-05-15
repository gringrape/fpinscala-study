import org.scalatest.funsuite.AnyFunSuite;

import Lazy.plus

class LazyTest extends AnyFunSuite {
  var count = 0;

  def clear() {
    count = 0
  }

  // 목표: 인자가 평가되는 횟수를 카운트
  def lazyArgument() = {
    count += 1
    1
  }

  def lazyFunction(a: => Int): Int = {
    a + a + a
  }

  def cashingFunction(a: => Int): Int = {
    lazy val cashA = a
    cashA + cashA + cashA
  }

  test("3 번 평가하지 않을까?") {
    clear()
    assert(lazyFunction(lazyArgument()) == 3)
    assert(count == 3)
  }

  test("캐싱을 통해 평가 반복피하기") {
    clear()
    assert(cashingFunction(lazyArgument()) == 3)
    assert(count == 1)
  }
}
