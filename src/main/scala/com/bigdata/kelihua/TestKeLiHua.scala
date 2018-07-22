package com.bigdata.kelihua



object TestKeLiHua {

  def m(x: Int) = (y: Int) => {
    x * y
  }

  def m2(x: Int)(implicit y: Int = 100) = x * y


  def m1(x: Int)(y: Int) = x + y

  def a(x: Int) = {
    x * 2
  }

  val a1 = (x: Int, y: Int) => x * y


  def main(args: Array[String]): Unit = {
    import MyContext._
    println(m2(22))
    println(m2(330))
  }
}

object MyContext {
  //这里起名为一个字母的时候会出现问题
  implicit val abc: Int = 666
}