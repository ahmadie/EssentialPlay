def cube(x: Int): Int = x * x * x

sum(x => x * x* x)(1,2)

def factorial(n: Int): Int =
  if (n == 0) 1 else n * factorial(n-1)

tailFac(3)

def tailFac(n: Int): Int = {
  def loop(acc: Int, n: Int): Int =
  if (n == 0) acc
  else loop(acc * n, n-1)

  loop(1, n)
}

def sumInts (f: Int => Int, a: Int, b: Int): Int =
  if(a > b) 0 else f(a) + sumInts(f, a + 1, b)

def tailSum(f: Int => Int, a: Int, b: Int ): Int ={
  def loop(a: Int, acc: Int): Int = {
    if (a > b) acc
    else loop (a+1, acc + f(a))
  }
  loop(a,0)
}

tailSum(x => x * x,3,5)

def divideXByY(x: Int, y: Int): Either[String, Int] = {
  if (y == 0) Left("Dude, can't divide by 0")
  else Right(x / y)
}

// a few different ways to use Either, Left, and Right
println(divideXByY(1, 0))
println(divideXByY(10, 5))

divideXByY(10, 0) match {
  case Left(s) => println("left-Answer:w " + s)
  case Right(i) => println("right-Answer: " + i)
}

def sum(f: Int => Int): (Int,Int) => Int = {
  def sumF(a: Int, b: Int) : Int =
    if (a > b) 0
    else f(a) + sumF(a + 1, b)
  sumF
}

product (x => x)(1,3)

def product(f: Int => Int)(a: Int, b: Int) : Int =
  if (a > b) 1 else f(a) * product(f)(a + 1, b)









