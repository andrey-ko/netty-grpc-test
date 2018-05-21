package org.andreyko.netty.grpc.test.organization.service

import dagger.*
import io.grpc.*
import io.grpc.netty.*
import io.netty.channel.nio.*
import io.netty.util.concurrent.*
import org.andreyko.netty.grpc.test.utils.*
import javax.inject.*

@Module
class AppModule(
  val appCfg: OrganizationServerConfig
) {
  
  constructor(vararg args: String) : this(parseArgs<OrganizationServerConfig>(*args))
  
  
  @Provides
  @Singleton
  fun provideWorkerGroup(): NioEventLoopGroup {
    return NioEventLoopGroup(1, DefaultThreadFactory("worker-group", true))
  }
  
  
  @Provides
  fun provideServer(service: OrganizationService, cfg: OrganizationServerConfig, workerGroup: NioEventLoopGroup): Server {
    return NettyServerBuilder.forPort(cfg.port)
      .executor({runnable->
        runnable.run()
      })
      .workerEventLoopGroup(workerGroup)
      .addService(service)
      .build()
  }
  
  @Provides
  @Singleton
  fun provideServerConfig(): OrganizationServerConfig {
    return appCfg
  }
}