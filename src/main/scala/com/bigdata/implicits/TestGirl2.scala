package com.bigdata.implicits

/*
//这里是柯里化和隐式转换相结合
def choose(first: T, second: T)(implicit ord: T => Ordered[T]): T = {
     if (first > second) first else second
   }

*/
/**
  * <% 可以将T 隐身转换车成Comparable[T]
  * <% 叫 视图界定 把T类型的实例转换成Comparable[T]类型，如果T类型的实例不是Comparable[T]的子类，利用视图界定的形式就可以把T类型，隐式转换
  * 例如： 视图界定 把 Int 转换成 RichInt 而RichInt 是Comparatable[T]的子类，
  * 所以就可以调用compareTo()
  *
  * @tparam T
  */
/*
//viewbound必须存在一个隐式转换方法
class TestGirl2[T <% Ordered[T]] {
  def choose(first: T, second: T): T = {
    if (first > second) first else second
  }
}
*/
class TestGirl2[T] {
  def choose(first: T, second: T)(implicit ord: T => Ordered[T]): T = {
    if (first > second) first else second
  }

  def select(first: T, second: T)(implicit ord: Ordering[T]): T = {
    if (ord.gt(first, second)) first else second
  }

  def random(first: T, second: T)(implicit ord: Ordering[T]): T = {
    import Ordered.orderingToOrdered
    if (first > second) first else second
  }

}

object TestGirl2 {
  def main(args: Array[String]): Unit = {
    import MyPreDef._
    val g = new TestGirl2[Girl]
    val g1 = new Girl("hatanao", 98, 98)
    val g2 = new Girl("sora", 99, 99)
    val gg = g.choose(g1, g2)
    println(gg.name)
  }
}
