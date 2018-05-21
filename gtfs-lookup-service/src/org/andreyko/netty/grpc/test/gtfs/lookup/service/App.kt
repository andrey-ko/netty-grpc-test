package org.andreyko.netty.grpc.test.gtfs.lookup.service

import com.satori.libs.common.kotlin.json.*
import org.slf4j.*

object App : IJsonContext by DefaultJsonContext {
  val log = LoggerFactory.getLogger(javaClass)
  
  @JvmStatic
  fun main(vararg args: String) {
    
    val component = DaggerAppComponent.builder()
      .appModule(AppModule(*args))
      .build()
    
    val server = component.newServer().apply {
      start()
      log.info("server started, listening on port $port")
    }
    
    Runtime.getRuntime().addShutdownHook(object : Thread() {
      override fun run() {
        System.err.println("stopping server, reason: jvm is shutting down")
        server.shutdown()
      }
    })
    
    server.awaitTermination()
  }
}