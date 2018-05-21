package org.andreyko.netty.grpc.test.health.check.service;

import dagger.*
import io.grpc.*
import io.grpc.internal.*
import io.grpc.netty.*
import io.grpc.util.*
import io.netty.channel.nio.*
import org.andreyko.netty.grpc.test.organization.service.*
import org.andreyko.vertx.protobuf.services.health.check.service.*
import org.slf4j.*
import javax.inject.*

@Module
class HealthCheckModule {
  private val log = LoggerFactory.getLogger(javaClass)
  
  @Provides
  @Singleton
  fun provideClient(cfg: HealthCheckClientConfig, eventLoopGroup: NioEventLoopGroup):  HealthCheckGrpc.HealthCheckStub {
    log.info("creating organization client...")
    val channel = NettyChannelBuilder.forAddress(cfg.host, cfg.port)
      .eventLoopGroup(eventLoopGroup)
      .nameResolverFactory(DnsNameResolverProvider())
      .loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance())
      .usePlaintext()
      .build()
    return HealthCheckGrpc.newStub(channel)
  }
  
}