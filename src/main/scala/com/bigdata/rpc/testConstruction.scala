package com.bigdata.rpc

class testConstruction(var id: String ="1", var name: String ="1") {

 /* def this(id: String,name: String,gender:String ){
    this(id,name)
  }*/
}

object testConstruction{
  def main(args: Array[String]): Unit = {
    val test = new testConstruction()
    println(s"${test.id} ++ ${test.name}")

  }
}
