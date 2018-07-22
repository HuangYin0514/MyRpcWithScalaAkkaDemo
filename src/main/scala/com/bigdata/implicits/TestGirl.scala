package com.bigdata.implicits

class TestGirl[T: Ordering] {

  def choose(first: T, second: T): T = {
    val ord = implicitly[Ordering[T]]
    if (ord.gt(first, second)) first else second
  }

}

object MissLeft {
  def main(args: Array[String]): Unit = {

    import MyPreDef._
    val g = new TestGirl[Girl]
    val g1 = new Girl("hatanao",98,98)
    val g2 = new Girl("sora",99,99)
    val gg = g.choose(g1,g2)
    println(gg.name)
  }
}
