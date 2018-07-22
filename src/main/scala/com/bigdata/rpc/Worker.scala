package com.bigdata.rpc

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

/**
  * Created with IDEA by User1071324110@qq.com on 2018/7/20
  *
  * @author 10713
  *
  */
class Worker(val masterHost: String, val masterPort: Int, val memory: Int, val cores: Int) extends Actor {

  var master: ActorSelection = _
  val workerId = UUID.randomUUID().toString
  //心跳间隔时间
  val HEART_INTERVAL = 10000

  //一般为小弟向大哥汇报
  override def preStart(): Unit = {
    //在master启动时会打印下面的那个协议, 可以先用这个做一个标志, 连接哪个master
    //继承actor后会有一个context, 可以通过它来连接
    //需要有/user, Master要和master那边创建的名字保持一致
    master = context.actorSelection(s"akka.tcp://${Master.ACTORSYSTEM_NAME}@$masterHost:$masterPort/user/${Master.ACTOR_NAME}")
    master ! RegisterWorker(workerId, memory, cores)
  }

  override def receive: Receive = {
    case "reply" => {
      println("a reply from master")
    }
    case RegisteredWorker(masterUrl) => {
      import context.dispatcher //使用timer太low了, 可以使用akka的, 使用定时器, 要导入这个包
      context.system.scheduler.schedule(0 millis, HEART_INTERVAL millis, self, SendHeartbeat)
    }
    case SendHeartbeat => {
      println("send heartbeat to master")
      master ! Heartbeat(workerId)
    }

  }
}

object Worker {
  def main(args: Array[String]): Unit = {
    val host = args(0)
    val port = args(1)
    val masterHost = args(2)
    val masterPort = args(3).toInt
    val memory = args(4).toInt
    val cores = args(5).toInt

    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val actorSystem = ActorSystem("WorkSystem", config)
    val myWorker = actorSystem.actorOf(Props(new Worker(masterHost, masterPort, memory, cores)), "myWorker")
    actorSystem.awaitTermination()
  }
}
