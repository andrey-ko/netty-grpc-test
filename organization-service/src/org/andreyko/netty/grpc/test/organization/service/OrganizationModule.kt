package org.andreyko.netty.grpc.test.organization.service;

import dagger.*
import io.grpc.*
import io.grpc.internal.*
import io.grpc.netty.*
import io.grpc.util.*
import io.netty.channel.nio.*
import org.slf4j.*
import javax.inject.*

@Module
class OrganizationModule {
  private val log = LoggerFactory.getLogger(javaClass)
  
  @Provides
  @Singleton
  fun provideClient(cfg: OrganizationClientConfig, eventLoopGroup: NioEventLoopGroup): OrganizationGrpc.OrganizationStub {
    log.info("creating organization client...")
    
    val nameResolver = DnsNameResolverProvider()
    val channel = NettyChannelBuilder.forTarget("dns:///${cfg.host}:${cfg.port}")
      .eventLoopGroup(eventLoopGroup)
      .nameResolverFactory(nameResolver)
      .loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance())
      
      .usePlaintext()
      .build()
  
    
    
    //(nameResolver as NameResolver).refresh()
  
    //nameResolver.newNameResolver()
    
    return OrganizationGrpc.newStub(channel)
  }
  
}