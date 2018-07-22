package com.bigdata.implicits

class TestBoy[T] {

  def choose[T <: Comparable[T]](first: T, second: T): T = {
    if (first.compareTo(second) > 0) first else second
  }

}

object TestBoy {
  def main(args: Array[String]): Unit = {
    val tb = new TestBoy[Boy]
    val b1 = new Boy("zhangsan", 99)
    val b2 = new Boy("lisi", 99)
    val b = tb.choose(b1, b2)
    println(b.name)

  }
}
