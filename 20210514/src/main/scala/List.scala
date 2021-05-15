package fpinscala.datastructures

sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ns: List[Int]): Int = ns match {
    case Nil         => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def sum2(ns: List[Int]): Int = foldRight[Int](ns, 0, _ + _)

  def product(ns: List[Int]): Int = ns match {
    case Nil        => 1
    case Cons(h, t) => h * product(t)
  }

  def lazyProductOf(a: Int, b: => Int) = {
    if (a == 0) 0
    else a * b
  }

  def foldRight[A](list: List[A], z: A, f: (A, => A) => A): A =
    list match {
      case Nil => z
      case Cons(h, t) => {
        // println(1)
        f(h, foldRight(t, z, f))
      }
    }

  def product2(ns: List[Int]) = foldRight[Int](ns, 1, lazyProductOf)

  // def concat(strings: List[String]) = foldRight[String](strings, "", _ + _)

  // def length(list: List[Int]) = foldRight[Int](list, 0, (a, b) => b + 1)

  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
}
