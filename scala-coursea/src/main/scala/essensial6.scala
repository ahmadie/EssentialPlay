/**
  * Created by buhari-pc on 2017/06/12.
  */
object essensial6 extends App {

  val people = Set(
    "Alice",
    "Bob",
    "Charlie",
    "Derek",
    "Edith",
    "Fred")

  val ages = Map(
    "Alice"   -> 20,
    "Bob"     -> 30,
    "Charlie" -> 50,
    "Derek"   -> 40,
    "Edith"   -> 10,
    "Fred"    -> 60)

  val favoriteColors = Map(
    "Bob"     -> "green",
    "Derek"   -> "magenta",
    "Fred"    -> "yellow")

  val oldest: Option[String] =
    people.foldLeft(Option.empty[String]) { (older, person) =>
      if(ages.getOrElse(person, 0) > older.flatMap(ages.get).getOrElse(0)) {
        Some(person)
      } else {
        older
      }
    }

  println(ages.getOrElse("Bob", 0))
  println(older.flatMap(ages.get).getOrElse(0))


  val favorite: Option[String] =
    for {
      oldest <- oldest
      color  <- favoriteColors.get(oldest)
    } yield color

  println(favorite)
}
