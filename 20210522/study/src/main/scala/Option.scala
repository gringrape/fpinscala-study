package exception

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None    => None
    case Some(a) => Some(f(a))
  }

  def getOrElse[B >: A](default: => B) = this match {
    case None    => default
    case Some(a) => a
  }

  def filter(f: A => Boolean) = this match {
    case Some(a) =>
      if (f(a)) this
    case _ => None
  }
}
case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

object Option {
  def mean(xs: Seq[Double]): Option[Double] = {
    if (xs.isEmpty) None
    else
      Some(
        (xs.sum / xs.length)
      )
  }
}
