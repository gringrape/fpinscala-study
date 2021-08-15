package simplerng

trait RNG {
  def nextInt: (Int, RNG)
}

object RNG {
  case class SimpleRNG(seed: Long) extends RNG {
    def nextInt: (Int, RNG) = {
      val newSeed = (seed * 0x5deece66dL + 0xbL) & 0xffffffffffffL
      val nextRNG = SimpleRNG(newSeed)
      val n = (newSeed >>> 16).toInt
      (n, nextRNG)
    }
  }

  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val (n, nextRNG) = rng.nextInt
    if (n < 0) ((n * -1) + 1, nextRNG)
    else (n, nextRNG)
  }

  def double(rng: RNG): (Double, RNG) = {
    val (n, nextRNG) = nonNegativeInt(rng)
    (n.toDouble / Int.MaxValue, nextRNG)
  }

  def intDouble(rng: RNG) = {
    val (num, nextRNG) = nonNegativeInt(rng)
    val (num2, nextDobuleRNG) = double(nextRNG)
    ((num, num2), nextDobuleRNG)
  }

  def ints(count: Int)(rng: RNG) = {
    List
      .range(0, count)
      .foldRight((List(): List[Int], rng))({
        case (_, (list, currentRNG)) => {
          val (num, nextRng) = currentRNG.nextInt
          (list.appended(num), nextRng)
        }
      })
  }

  type Rand[+A] = RNG => (A, RNG)

  def unit(a: Int): Rand[Int] = (rng: RNG) => (a, rng)
  def map[A, B](rand: Rand[A])(f: A => B): Rand[B] = { rng =>
    {
      val (a, rng2) = rand(rng)
      (f(a), rng2)
    }
  }

  def toDecimal(n: Int) = n.toDouble / Int.MaxValue

  def _double: Rand[Double] = map(rng => rng.nextInt)(toDecimal)

  def map2[A, B, C](ra: Rand[A], rb: Rand[B])(
      f: (A, B) => (Int, Double)
  ): Rand[(Int, Double)] = rng => ((1, 0.1), SimpleRNG(1))

