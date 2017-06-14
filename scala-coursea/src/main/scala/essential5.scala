object collection extends App {
  case class Film(
                   name: String,
                   yearOfRelease: Int,
                   imdbRating: Double)

  case class Director(
                       firstName: String,
                       lastName: String,
                       yearOfBirth: Int,
                       films: Seq[Film])

  val memento           = new Film("Memento", 2000, 8.5)
  val darkKnight        = new Film("Dark Knight", 2008, 9.0)
  val inception         = new Film("Inception", 2010, 8.8)

  val highPlainsDrifter = new Film("High Plains Drifter", 1973, 7.7)
  val outlawJoseyWales  = new Film("The Outlaw Josey Wales", 1976, 7.9)
  val unforgiven        = new Film("Unforgiven", 1992, 8.3)
  val granTorino        = new Film("Gran Torino", 2008, 8.2)
  val invictus          = new Film("Invictus", 2009, 7.4)

  val predator          = new Film("Predator", 1987, 7.9)
  val dieHard           = new Film("Die Hard", 1988, 8.3)
  val huntForRedOctober = new Film("The Hunt for Red October", 1990, 7.6)
  val thomasCrownAffair = new Film("The Thomas Crown Affair", 1999, 6.8)

  val eastwood = new Director("Clint", "Eastwood", 1930,
    Seq(highPlainsDrifter, outlawJoseyWales, unforgiven, granTorino, invictus))

  val mcTiernan = new Director("John", "McTiernan", 1951,
    Seq(predator, dieHard, huntForRedOctober, thomasCrownAffair))

  val nolan = new Director("Christopher", "Nolan", 1970,
    Seq(memento, darkKnight, inception))

  val someGuy = new Director("Just", "Some Guy", 1990,
    Seq())

  val directors = Seq(eastwood, mcTiernan, nolan, someGuy)

  // TODO: Write your code here!


  println(nolan.films.map(_.name))

  println(mcTiernan.films.foldLeft(23123131) { (current, film) =>
    math.min(current, film.yearOfRelease)
  })


  val films = directors.flatMap(director => director.films)

  println(films.foldLeft(0:Double)((a, b) => a + b.imdbRating) / films.length);

}

/**
  * Created by buhari-pc on 2017/06/10.
  */
object essential5 extends App {
  val list = List(1, 2, 3)
  list.flatMap(x => List(x, -x))

  sealed trait Maybe[+A] {
    def fold[B](full: A => B, empty: B): B =
      this match {
        case Full(v) => full(v)
        case Empty() => empty
      }

    def flatMap[B](fn: A => Maybe[B]): Maybe[B] =
      this match {
        case Full(v) => fn(v)
        case Empty() => Empty[B]()
      }

    def map[B](fn: A => B): Maybe[B] =
      flatMap[B](v => Full(fn(v)))
  }

  final case class Full[A](value: A) extends Maybe[A]
  final case class Empty[A]() extends Maybe[A]

  val full = Full(3)
  val fullafterflatMap = full flatMap (x => Full(x))

  val maybleList = List(Full(3), Full(2), Full(1))
  maybleList.map(maybe => maybe flatMap ( x => if(x % 2 == 0) Full(x) else Empty() ))
}

//////////////////////


sealed trait Sum[+A, +B] {
  def flatMap[AA >: A, C](f: B => Sum[AA, C]): Sum[AA, C] =
    this match {
      case Failure(v) => Failure(v)
      case Success(v) => f(v)
    }
}
final case class Failure[A](value: A) extends Sum[A, Nothing]
final case class Success[B](value: B) extends Sum[Nothing, B]

sealed trait Expression {
  def eval: Sum[String, Double] =
    this match {
      case Addition(l, r) => lift2(l, r, (left, right) => Success(left + right))
      case Subtraction(l, r) => lift2(l, r, (left, right) => Success(left - right))
      case Division(l, r) => lift2(l, r, (left, right) =>
        if(right == 0)
          Failure("Division by zero")
        else
          Success(left / right)
      )
      case SquareRoot(v) =>
        v.eval flatMap { value =>
          if(value < 0)
            Failure("Square root of negative number")
          else
            Success(Math.sqrt(value))
        }
      case Number(v) => Success(v)
    }

  def lift2(l: Expression, r: Expression, f: (Double, Double) => Sum[String, Double]) =
    l.eval flatMap { left =>
      r.eval flatMap { right =>
        f(left, right)
      }
    }
}


final case class Addition(left: Expression, right: Expression) extends Expression
final case class Subtraction(left: Expression, right: Expression) extends Expression
final case class Division(left: Expression, right: Expression) extends Expression
final case class SquareRoot(value: Expression) extends Expression
final case class Number(value: Double) extends Expression


object test extends App{
  assert(Addition(Number(1), Number(2)).eval == Success(3))
  assert(SquareRoot(Number(-1)).eval == Failure("Square root of negative number"))
  assert(Division(Number(4), Number(0)).eval == Failure("Division by zero"))
  assert(Division(Addition(Subtraction(Number(8), Number(6)), Number(2)), Number(2)).eval == Success(2.0))
}

