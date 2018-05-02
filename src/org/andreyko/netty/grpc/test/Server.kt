package org.andreyko.netty.grpc.test

import io.grpc.*
import org.slf4j.*

object Server {
  val log = LoggerFactory.getLogger(javaClass)
  
  @JvmStatic
  fun main(vararg args: String) {
    
    val port = 8080
    val server = ServerBuilder.forPort(port)
      .addService(GtfsLookupService())
      .build()
    server.start()
    
    log.info("server started, listening on $port")
    Runtime.getRuntime().addShutdownHook(object : Thread() {
      override fun run() {
        System.err.println("jvm is shutting down")
        server.shutdown()
      }
    })
  
    server.awaitTermination();
  }
}