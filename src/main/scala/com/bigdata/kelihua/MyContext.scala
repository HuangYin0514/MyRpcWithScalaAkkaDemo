package com.bigdata.kelihua

object MyContext {
  //这里起名为一个字母的时候会出现问题
  implicit val abc: Int = 666
}
