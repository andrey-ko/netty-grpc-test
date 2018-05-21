package org.andreyko.netty.grpc.test.gtfs.lookup.service;

import dagger.*
import io.grpc.*
import io.grpc.internal.*
import io.grpc.netty.*
import io.grpc.util.*
import io.netty.channel.nio.*
import org.slf4j.*
import javax.inject.*

@Module
class GtfsLookupModule {
  private val log = LoggerFactory.getLogger(javaClass)
  
  @Provides
  @Singleton
  fun provideClient(cfg: GtfsLookupClientConfig, eventLoopGroup: NioEventLoopGroup): GtfsLookupGrpc.GtfsLookupStub {
    log.info("creating gtfs-lookup client...")
    val channel = NettyChannelBuilder.forAddress(cfg.host, cfg.port)
      .eventLoopGroup(eventLoopGroup)
      //.nameResolverFactory(DnsNameResolverProvider())
      //.loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance())
      .usePlaintext()
      .build()
    return GtfsLookupGrpc.newStub(channel)
  }
}