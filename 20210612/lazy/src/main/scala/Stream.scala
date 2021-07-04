package stream

import Stream._
import scala.annotation.tailrec

sealed trait Stream[+A] {
  def toList(): List[A] = this match {
    case Empty      => Nil
    case Cons(h, t) => h() :: t().toList()
  }

  def exists(f: A => Boolean): Boolean = this match {
    case Cons(h, t) => f(h()) || t().exists(f)
    case Empty      => false
  }

  def foldRight[B](z: B)(f: (A, => B) => B): B = this match {
    case Cons(h, t) => f(h(), t().foldRight(z)(f))
    case _          => z
  }

  def exists2(f: A => Boolean) = foldRight(false)((a, b) => f(a) || b)

  def forAll(f: A => Boolean) = foldRight(true)((a, b) => f(a) && b)

  def take(n: Int): Stream[A] = this match {
    case Cons(h, t) if n >= 1 => cons(h(), t().take(n - 1))
    // case Cons(h, _) if n == 1 => cons(h(), empty)
    case _ => empty
  }

  def drop(n: Int): Stream[A] = this match {
    case Cons(h, t) if n > 0 => t().drop(n - 1)
    case _                   => this
  }

  def takeWhile(p: A => Boolean): Stream[A] = this match {
    case Cons(h, t) if p(h()) => cons(h(), t().takeWhile(p))
    case _                    => empty
  }
}
case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  def cons[A](hd: => A, tl: => Stream[A]) = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))

  def constant2(n: Int): Stream[Int] =
    cons(n, constant2(n))

  def from(n: Int): Stream[Int] = cons(n, from(n + 1))

  def fibs() = {
    def go(a: Int = 0, b: Int = 1): Stream[Int] =
      cons(a, go(b, a + b))
    go()
  }

  def unfold[S](z: S)(f: S => Option[S]): Stream[S] = f(z) match {
    case Some(h) => cons(z, unfold(h)(f))
    case None    => cons(z, empty)
  }

  def onesViaUnfold() = unfold(1)((_ => Some(1)))

  def fromViaUnfold(n: Int) = unfold(n)(a => Some(a + 1))

  def fibsViaUnfold() = {
    def go(a: Int = 0, b: Int = 1) = unfold(s)

    go()
  }
}
