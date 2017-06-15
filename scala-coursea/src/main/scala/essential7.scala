/**
  * Created by a.bukhari on 2017/06/14.
  */
object essential7 extends App{

  trait Equal[A] {
    def equal(v1: A, v2: A): Boolean
  }

  object Eq {
    def apply[A](v1: A, v2: A)(implicit equal: Equal[A]): Boolean =
      equal.equal(v1, v2)
  }

  object Equal {
    def apply[A](implicit instance: Equal[A]): Equal[A] =
      instance

    implicit class ToEqual[A](in: A) {
      def ===(other: A)(implicit equal: Equal[A]): Boolean =
        equal.equal(in, other)
    }

  }

  object EmailEqual extends Equal[Person] {
    def equal(v1: Person, v2: Person): Boolean =
      v1.email == v2.email
  }

  implicit object NameEmailEqual extends Equal[Person] {
    def equal(v1: Person, v2: Person): Boolean =
      v1.email == v2.email && v1.name == v2.name
  }

  implicit val caseInsensitiveEquals = new Equal[String] {
    def equal(s1: String, s2: String) =
      s1.toLowerCase == s2.toLowerCase
  }


  case class Person(name:String, email:String)

  Eq(Person("Noel", "noel@example.com"), Person("Noel", "noel@example.com"))
  Equal[Person].equal(Person("Noel", "noel@example.com"), Person("Noel", "noel@example.com"))

  import Equal._
  "foo".===("FOO")

}
