object MultipleGenerators extends App {

  trait Generator[+T] {
    self =>

    def generate: T

    def map[S](f: T => S): Generator[S] = new Generator[S] {
      override def generate: S = f(self.generate)
    }

    def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
      override def generate = f(self.generate).generate
    }
  }

  val integers = new Generator[Int] {
    val rand = new java.util.Random
    def generate = rand.nextInt()
  }

  val booleans = new Generator[Boolean] {
    def generate = integers.generate > 0
  }

  val newbooleans = for ( x <- integers) yield x > 0
//  val mapboolean = new Generator[Boolean] {
//    override def generate = this.map[S](x => x > 0)(integers.generate)
//  }

  val pairs = new Generator[(Int, Int)] {
    def generate = (integers.generate, integers.generate)
  }



  println("booleans " + booleans.generate)
  println("newbooleans " + newbooleans.generate)
  //println("mapboolean " + mapboolean.generate)



}