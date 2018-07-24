package com.bigdata.implicits


//如果使用上下文界定，必须存在一个隐式转换的值
//[T: Ordering] 这种写法 说明存在一个隐式类型Ordering[T]
class TestGirl[T: Ordering] {
  def choose(first: T, second: T): T = {
    //获取隐式对象
    val ord = implicitly[Ordering[T]]
    if (ord.gt(first, second)) first else second
  }
}

object MissLeft {
  def main(args: Array[String]): Unit = {

    import MyPreDef._
    val g = new TestGirl[Girl]
    val g1 = new Girl("hatanao", 98, 98)
    val g2 = new Girl("sora", 99, 99)
    val gg = g.choose(g1, g2)
    println(gg.name)
  }
}
