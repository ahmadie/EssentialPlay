def sum(xs:List[Int]) =
  0 :: xs reduceLeft ((x, y) => x + y)


def sum2(xs:List[Int]) =
  0 :: xs reduceLeft (_ + _)

sum2(List(1,2))

val n = 7

(1 until n) flatMap (i =>
  (1 until i) map (j => (i,j)))

