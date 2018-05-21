package org.andreyko.netty.grpc.test.health.check.service

import io.grpc.netty.*
import org.slf4j.*

object App {
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
  
  /*@JvmStatic
  fun main(vararg args: String) {
    val hzCfg = Config()
    //hzCfg.setProperty("hazelcast.logging.type", "slf4j");
    System.setProperty("vertx.hazelcast.async-api", "true")
    val clusterManager = HazelcastClusterManager(hzCfg)
    val vertxOptions = VertxOptions().apply {
      this.clusterManager = clusterManager
      this.isClustered = true
    }
    
    Vertx.clusteredVertx(vertxOptions) { ar ->
      if (!ar.succeeded()) {
        log.error("failed to create vertx", ar.cause())
        return@clusteredVertx
      }
      val vertx = ar.result()
      vertx.exceptionHandler { exn ->
        vertx.close { ar ->
          clusterManager.hazelcastInstance.shutdown()
          System.runFinalization()
          System.exit(-1) // TODO: fix it
        }
      }
      log.info("vertx successfully created")
      val deploymentOptions = DeploymentOptions().apply {
      }
      val component = DaggerAppComponent.builder()
        .appModule(AppModule(vertx, clusterManager.hazelcastInstance))
        .build()
      vertx.deployVerticle(component.newHealthCheckVerticle()) { deploymentResult ->
        if (!deploymentResult.succeeded()) {
          //log.error("failed to deploy verticle", deploymentResult.cause())
        }
        log.info("verticle successfully deployed(deploymentId=${deploymentResult.result()})")
      }
    }
  }*/
}