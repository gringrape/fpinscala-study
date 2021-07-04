package stream

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
    case Cons(h, t) if (n > 0) => Cons(h, () => t().take(n - 1))
    case _                     => Empty
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

  def from(n: Int): Stream[Int] = Cons(() => n, () => from(n + 1))

  def fibs(): Stream[Int] = {
    def go(a: Int, b: Int): Stream[Int] = Cons(() => a, () => go(b, a + b))
    go(0, 1)
  }
}
