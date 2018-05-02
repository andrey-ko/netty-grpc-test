package org.andreyko.netty.grpc.test

import io.grpc.*
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
    
    val stub = GtfsLookupGrpc.newBlockingStub(channel)
    val reply = stub.ping(PingRequest.newBuilder().apply {
      message = "hello world!"
    }.build())
    println("server reply: '${reply.message}'")
    println("done")
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }
}