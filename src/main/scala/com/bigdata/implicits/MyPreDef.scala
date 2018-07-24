package com.bigdata.implicits

import java.io.File


//这里相当于门面
object MyPreDef {

  //当File中没有定义相关方法时,会去object中寻找是否有隐式转换
  //如果有隐式转换,那么进行隐式转换,(将file进行装饰)
  //调用隐式转换中的方法进行处理
  implicit def file2RichFile(file: File) = new RichFile(file)

  /*
    //隐式伴生对象
    implicit object girl2Ordering extends Ordering[Girl] {
      override def compare(x: Girl, y: Girl): Int = {
        if (x.faceValue == y.faceValue) {
          x.size - y.size
        } else {
          x.faceValue - y.faceValue
        }
      }
    }
    */

  //方法一 类
  trait girl2Ordering extends Ordering[Girl] {
    override def compare(x: Girl, y: Girl): Int = {
      if (x.faceValue == y.faceValue) {
        x.size - y.size
      } else {
        x.faceValue - y.faceValue
      }
    }
  }

  implicit object girl2OrderingObject extends girl2Ordering


  //方法二 函数
  // 在无参数的时候 退化成
  // val  girl2Ordering :  Ordering[Girl] = ...
  implicit val girl2Ordering = new Ordering[Girl] {
    override def compare(x: Girl, y: Girl): Int = {
      if (x.faceValue == y.faceValue) {
        x.size - y.size
      } else {
        x.faceValue - y.faceValue
      }
    }
  }

  //方法三 方法
  implicit def girl2Ordering2 = new Ordering[Girl] {
    override def compare(x: Girl, y: Girl): Int = {
      if (x.faceValue == y.faceValue) {
        x.size - y.size
      } else {
        x.faceValue - y.faceValue
      }
    }
  }


  //方法一 方法
    implicit def girl2Ordered(x: Girl) = new Ordered[Girl] {
      override def compare(y: Girl): Int = {
        if (x.faceValue == y.faceValue) {
          x.size - y.size
        } else {
          x.faceValue - y.faceValue
        }
      }
    }

  //方法二 函数
  implicit val girl2Ordered = (x: Girl) => new Ordered[Girl] {
    override def compare(y: Girl): Int = {
      if (x.faceValue == y.faceValue) {
        x.size - y.size
      } else {
        x.faceValue - y.faceValue
      }
    }
  }

  //方法三 object
  // 由于object 不能接受参数 ,所以object在有参数的时候无法实现
}
