package com.bigdata.implicits

import java.io.File

object MyPreDef {

  //当File中没有定义相关方法时,会去object中寻找是否有隐式转换
  //如果有隐式转换,那么进行隐式转换
  //调用隐式转换中的方法进行处理
  implicit def file2RichFile(file:File) = new RichFile(file)

}