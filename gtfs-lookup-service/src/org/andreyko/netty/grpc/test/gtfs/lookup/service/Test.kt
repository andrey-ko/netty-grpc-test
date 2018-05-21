package org.andreyko.netty.grpc.test.gtfs.lookup.service

import com.satori.libs.common.kotlin.json.*
import io.grpc.*
import org.slf4j.*

object Test: IJsonContext by DefaultJsonContext {
  val log = LoggerFactory.getLogger(javaClass)
  
  @JvmStatic
  fun main(vararg args: String) {
  
    App.main("--port", "9090")
  }
}