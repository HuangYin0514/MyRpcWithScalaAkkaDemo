package com.bigdata.rpc

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created with IDEA by User1071324110@qq.com on 2018/7/20
  *
  * @author 10713
  *
  */
class Master extends Actor {

  println("constructor invoked")

  override def preStart(): Unit = {
    println("preStart invoked")
  }

  override def receive: Receive = {
    case "connect" => {
      println("a client connected")
      sender ! "reply"
    }

    case "hello" => {
      println("master hello")
    }
  }
}

object Master {

  val ACTORSYSTEM_NAME = "MasterSystem"
  val ACTOR_NAME = "myMaster"

  def main(args: Array[String]): Unit = {
    val host = args(0)
    val port = args(1).toInt
    //准备配置
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    //ActorSystem老大，辅助创建和监控下面的Actor，他是单例的
    val actorSystem = ActorSystem(ACTORSYSTEM_NAME, config)
    //创建Actor, 起个名字
    val master = actorSystem.actorOf(Props[Master], ACTOR_NAME) //Master主构造器会执行
    master ! "hello" //发送信息
    actorSystem.awaitTermination() //让进程等待着, 先别结束
  }
}
