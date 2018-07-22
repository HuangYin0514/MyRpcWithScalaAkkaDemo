package com.bigdata.rpc

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.duration._

/**
  * Created with IDEA by User1071324110@qq.com on 2018/7/20
  *
  * @author 10713
  *
  */
class Master(val host: String, val port: Int) extends Actor {

  // workerId -> WorkerInfo
  val idToWorker = new mutable.HashMap[String, WorkerInfo]()
  // WorkerInfo
  val workers = new mutable.HashSet[WorkerInfo]()  //使用set删除快, 也可用linkList
  //超时检查时间间隔
  val CHECK_INTERVAL = 15000

  override def preStart(): Unit = {
    println("preStart invoked")
    import context.dispatcher //使用timer太low了, 可以使用akka的, 使用定时器, 要导入这个包
    context.system.scheduler.schedule(0 millis, CHECK_INTERVAL millis, self, CheckTimeOutWorker)
  }

  override def receive: Receive = {
    case RegisterWorker(id, memory, cores) => {
      if (!idToWorker.contains(id)) {
        //把Worker的信息封装起来保存到内存当中
        val workerInfo = new WorkerInfo(id, memory, cores)
        idToWorker(id) = workerInfo
        workers += workerInfo
        sender ! RegisteredWorker(s"akka.tcp://${Master.ACTORSYSTEM_NAME}@$host:$port/user/${Master.ACTOR_NAME}") //通知worker注册
      }
    }
    case Heartbeat(id) => {
      if (idToWorker.contains(id)) {
        val workerInfo = idToWorker(id)
        //报活
        val currentTime = System.currentTimeMillis()
        workerInfo.lastHeartbeatTime = currentTime
      }
    }
    case CheckTimeOutWorker => {
      val currentTime = System.currentTimeMillis()
      val toRemove = workers.filter(x => currentTime - x.lastHeartbeatTime > CHECK_INTERVAL)
      for (w <- toRemove) {
        workers -= w
        idToWorker -= w.id
      }
      println(workers.size)
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
    val master = actorSystem.actorOf(Props(new Master(host, port)), ACTOR_NAME) //Master主构造器会执行
    master ! "hello" //发送信息
    actorSystem.awaitTermination() //让进程等待着, 先别结束
  }
}
