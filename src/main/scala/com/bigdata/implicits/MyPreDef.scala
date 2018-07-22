package com.bigdata.implicits

import java.io.File


//这里相当于门面
object MyPreDef {

  //当File中没有定义相关方法时,会去object中寻找是否有隐式转换
  //如果有隐式转换,那么进行隐式转换,(将file进行装饰)
  //调用隐式转换中的方法进行处理
  implicit def file2RichFile(file: File) = new RichFile(file)

  implicit object girl2Ordering extends Ordering[Girl] {
    override def compare(x: Girl, y: Girl): Int = {
      if (x.faceValue == y.faceValue) {
        x.size - y.size
      } else {
        x.faceValue - y.faceValue
      }
    }
  }

  implicit def girl2Ordered(x: Girl) = new Ordered[Girl] {

    override def compare(y: Girl): Int = {
      if (x.faceValue == y.faceValue) {
        x.size - y.size
      } else {
        x.faceValue - y.faceValue
      }
    }
  }


}
