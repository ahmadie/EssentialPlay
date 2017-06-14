




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
}

final case class Full[A](value: A) extends Maybe[A]
final case class Empty[A]() extends Maybe[A]

val maybleList = List(Full(3), Full(2), Full(1))
maybleList.map(maybe => maybe.flatMap( x => if( (x%2) == 0 ) Full(x) else Empty() ))

