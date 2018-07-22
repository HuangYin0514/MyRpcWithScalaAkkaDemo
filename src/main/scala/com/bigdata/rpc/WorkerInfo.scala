package com.bigdata.rpc

//如果不加val关键字，
// 如id:String 就会默认为 private[this] val id:String  外部无法访问
class WorkerInfo(val id: String, val memory: Int, val cores: Int) {

  var lastHeartbeatTime: Long = _
}
