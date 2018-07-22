package com.bigdata.implicits

class TestGirl2[T <% Ordered[T]] {

  /*
  def choose(first: T, second: T)(implicit ord: T => Ordered[T]): T = {
      if (first > second) first else second
    }
    */

  def choose[T <% Ordered[T]](first: T, second: T) {
    if (first > second) first else second
  }

  /*
    def select(first: T, second: T)(implicit ord : Ordering[T]): T ={
      if(ord.gt(first, second)) first else second
    }

    def random(first: T, second: T)(implicit ord : Ordering[T]): T ={
      import Ordered.orderingToOrdered
      if(first > second) first else second
    }
  */

}

object TestGirl2 {
  def main(args: Array[String]): Unit = {
    val g = new TestGirl2[Girl]
    val g1 = new Girl("hatanao", 98, 98)
    val g2 = new Girl("sora", 99, 99)
    import MyPreDef._
    val gg = g.choose(g1, g2)
    println(gg.name)
  }
}
