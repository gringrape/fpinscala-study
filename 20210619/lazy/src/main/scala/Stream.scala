package lazyStructures

sealed trait Stream[+A]
case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A] {
  def a = h()

  override def equals(that: Any): Boolean = that match {
    case Cons(h, t) =>
      println(this.h())
      println(h())
      this.h() == h() && this.t().equals(t())
  }
}

object Stream {
  // def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
  //   lazy val head = hd
  //   lazy val tail = tl
  //   Cons(() => head, () => tail)
  // }

  def apply[A](elements: A*): Stream[A] = {
    if (elements.isEmpty) Empty
    else Cons(() => elements.head, () => apply(elements.tail: _*))
    // Cons(() => 1, () => Cons(() => 2, () => Empty))
  }

}
