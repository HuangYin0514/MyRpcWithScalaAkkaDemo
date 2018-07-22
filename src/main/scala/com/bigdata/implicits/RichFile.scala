package com.bigdata.implicits

import java.io.File

import scala.io.Source

class RichFile(file: File) {

  def read() = {
    Source.fromFile(file).mkString
  }

}

object RichFile {
  def main(args: Array[String]): Unit = {
    val file = new File("C:\\Users\\10713\\Desktop\\a.txt")
    /*
        //装饰模式，显示的增强
        val rf = new RichFile(file)
        val context = rf.read()
        println(context)
    */
    //隐式转换；隐式的进行增强
    import MyPreDef._ //MyPreDef 不能放到该伴生对象的下面，因为object是从上到下编译的，放到下面则执行到此处未编译
    val context = file.read()
    println(context)
  }
}
