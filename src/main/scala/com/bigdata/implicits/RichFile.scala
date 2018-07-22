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
    import MyPreDef._
    val context = file.read()
    println(context)


  }
}