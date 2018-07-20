package com.bigdata.rpc

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created with IDEA by User1071324110@qq.com on 2018/7/20
  *
  * @author 10713
  *
  */
class Worker extends Actor {


  //一般为小弟向大哥汇报
  override def preStart(): Unit = {
    //在master启动时会打印下面的那个协议, 可以先用这个做一个标志, 连接哪个master
    //继承actor后会有一个context, 可以通过它来连接
    //需要有/user, Master要和master那边创建的名字保持一致
    val master = context.actorSelection(s"akka.tcp://MasterSystem@127.0.0.1:8888/user/myMaster")
    master ! "connect"
  }

  override def receive: Receive = {
    case "reply" => {
      println("a reply from master")
    }
  }
}

object Worker {
  def main(args: Array[String]): Unit = {
    val host = args(0)
    val port = args(1)
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val actorSystem = ActorSystem("WorkSystem", config)
    val myWorker = actorSystem.actorOf(Props[Worker], "myWorker")
    actorSystem.awaitTermination()
  }
}
