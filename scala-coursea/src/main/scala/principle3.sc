

val t1 = new NonEmpty(6,new Empty(),new Empty())
val t2 = new NonEmpty(7,new Empty(),new NonEmpty(8, new Empty(), new Empty()))

val t5 = t1 union t2


//t5 contains (4)

abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other:IntSet): IntSet
}

case class Empty() extends IntSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet =
    new NonEmpty (x, new Empty, new Empty)
  //override def toString = "."
  def union (other: IntSet): IntSet = other
}

case class NonEmpty(elem: Int, left: IntSet, right:IntSet) extends IntSet{
  def contains(x: Int): Boolean =
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true

  def incl(x: Int) : IntSet =
    if (x < elem) new NonEmpty (elem, left incl x, right)
    else if (x > elem) new NonEmpty (elem, left, right incl x)
    else this

  def union(other: IntSet): IntSet =
    ((left union right) union other) incl elem
  //override def toString = "{" + left + elem + right + "}"
}

//(6, l, r)
//((l union r) union (7, l ,(8 ,l ,r))) incl 6
//(7, (6, l, r),(8, l, r))


(7, l ,(8 ,l ,r)) u (6, l, r)

(8 ,l ,r) u (6, l, r) incl 7

((6, l, r)) incl 7 incl 8

//
//(6 , l, r)
//
//(6 , l, ( 8, l, r))
//
//(6 , l, ( 8, l, ( 10, l, r)))
//
//(6, (4, l, r),(8, l, (10, l, r)))
//
//(6, (4, l, r),(8, l, (10, l, r)))
//
//(6, (4, l, r),(8, l, (10, l, (11, l, r)))
//
//
//(6, (4, l, (5, l, r)), (8, l, (10, l, r)))
//
//(6, (4, l, (5, l, r)), (8, l, (10, l, r)))
