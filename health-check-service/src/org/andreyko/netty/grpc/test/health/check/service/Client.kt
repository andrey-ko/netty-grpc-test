package org.andreyko.netty.grpc.test.health.check.service

import com.google.protobuf.*
import io.grpc.*
import org.andreyko.vertx.protobuf.services.health.check.service.*
import org.slf4j.*
import java.util.concurrent.*

object Client {
  val log = LoggerFactory.getLogger(javaClass)
  
  @JvmStatic
  fun main(vararg args: String) {
    
    val channel = ManagedChannelBuilder.forAddress("localhost", 8080)
      // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
      // needing certificates.
      .usePlaintext()
      .build()
    
    val stub = HealthCheckGrpc.newBlockingStub(channel)
    val reply = stub.status(Empty.getDefaultInstance())
    println("server reply: {\n$reply}")
    println("done")
    channel.shutdown()
    channel.awaitTermination(5, TimeUnit.SECONDS)
  }
}