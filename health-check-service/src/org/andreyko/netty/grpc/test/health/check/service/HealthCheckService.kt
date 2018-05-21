package org.andreyko.netty.grpc.test.health.check.service

import com.google.protobuf.*
import com.satori.libs.async.core.*
import com.satori.libs.async.kotlin.*
import io.fabric8.kubernetes.client.*
import io.grpc.stub.*
import io.netty.channel.nio.*
import io.netty.util.concurrent.ScheduledFuture
import org.andreyko.netty.grpc.test.gtfs.lookup.service.*
import org.andreyko.netty.grpc.test.organization.service.*
import org.andreyko.vertx.protobuf.services.health.check.service.*
import org.slf4j.*
import java.util.*
import java.util.concurrent.*
import javax.inject.*
import kotlin.coroutines.experimental.*

class HealthCheckService @Inject constructor(
  val gtfsLookupService: GtfsLookupGrpc.GtfsLookupStub,
  val organizationService: OrganizationGrpc.OrganizationStub,
  val workerGroup: NioEventLoopGroup,
  val kubeClient: DefaultKubernetesClient
) : HealthCheckGrpc.HealthCheckImplBase() {
  val log = LoggerFactory.getLogger(javaClass)
  val status = HashMap<String, String>()
  var timerFuture: ScheduledFuture<*>? = null
  
  init {
    timerFuture = workerGroup.schedule(::doCheck, 1, TimeUnit.SECONDS)
  }
  
  fun stop() {
    val timerFuture = timerFuture
    this.timerFuture = null
    if (timerFuture != null) {
      timerFuture.cancel(true)
    }
  }
  
  fun doCheck() {
    this.timerFuture = null
    
    val afj = AsyncForkJoin()
    
    afj.fork {
      val result = try {
        val reply = awaitStreamOberver<org.andreyko.netty.grpc.test.gtfs.lookup.service.Status> { observer ->
          gtfsLookupService.status(Empty.getDefaultInstance(), observer)
        }
        log.info("reply message: '$reply'")
        "healthy"
      } catch (ex: Throwable) {
        log.warn("request failed", ex)
        "failure: $ex"
      }
      status.put("gtfs-lookup-service", result)
    }
    
    afj.fork {
      val result = try {
        val reply = awaitStreamOberver<org.andreyko.netty.grpc.test.organization.service.Status> { observer ->
          organizationService.status(Empty.getDefaultInstance(), observer)
        }
        log.info("reply message: '$reply'")
        "healthy"
      } catch (ex: Throwable) {
        log.warn("request failed", ex)
        "failure: $ex"
      }
      status.put("organization-service", result)
    }
    
    afj.fork {
      val result = try {
        val reply = awaitStreamOberver<org.andreyko.vertx.protobuf.services.health.check.service.Status>{ observer ->
          status(Empty.getDefaultInstance(), observer)
        }
        log.info("reply message: '$reply'")
        "healthy"
      } catch (ex: Throwable) {
        log.warn("request failed", ex)
        "failure: $ex"
      }
      status.put("health-check-service", result)
    }
    
    afj.fork {
      kubeClient
    }
    
    afj.join().onCompleted { ar ->
      timerFuture = workerGroup.schedule(::doCheck, 1, TimeUnit.SECONDS)
    }
  }
  
  override fun status(request: Empty?, responseObserver: StreamObserver<org.andreyko.vertx.protobuf.services.health.check.service.Status>) {
    log.info("status request received")
    responseObserver.onNext(org.andreyko.vertx.protobuf.services.health.check.service.Status.newBuilder().apply {
      name = MetaInfo.project
      version = MetaInfo.version
      sha = MetaInfo.gitSha
      api = "v0"
    }.build())
    responseObserver.onCompleted()
  }
  
  suspend inline fun <reified T> awaitStreamOberver(crossinline block: (StreamObserver<T>) -> Unit): T {
    return suspendCoroutine { cont ->
      block(object : StreamObserver<T> {
        var completed = false
        override fun onNext(value: T) {
          if (completed) throw IllegalStateException("already completed")
          completed = true
          cont.resume(value)
        }
        
        override fun onError(t: Throwable) {
          if (completed) throw IllegalStateException("already completed")
          completed = true
          cont.resumeWithException(t)
        }
        
        override fun onCompleted() {
          if (!completed) {
            cont.resumeWithException(Exception("request completed without result"))
          }
        }
      })
      
    }
    
  }
}