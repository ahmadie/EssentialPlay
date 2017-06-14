import Math.abs

def sqrIter(guess:Double, x:Double): Double =
  if (isGoodEnough(guess, x)) guess
  else sqrIter(improve(guess,x), x)


def isGoodEnough (guess:Double, x:Double) =
  abs(guess * guess - x) < 0.001

def improve(guess:Double, x:Double): Double =
   (guess + x / guess) / 2

def sqrt(x: Double) = sqrIter(1.0, x)

sqrt(2)


def factorial(n:Int): Int = {
  def factorialItr(sum:Int, n: Int): Int =
    if (n == 0) sum else factorialItr(sum*n, n-1)

  factorialItr(1,n)
}

factorial(3)