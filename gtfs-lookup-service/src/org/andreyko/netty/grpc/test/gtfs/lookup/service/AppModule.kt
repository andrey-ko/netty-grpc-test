package org.andreyko.netty.grpc.test.gtfs.lookup.service

import dagger.*
import io.grpc.*
import io.grpc.netty.*
import io.netty.channel.nio.*
import io.netty.util.concurrent.*
import org.andreyko.netty.grpc.test.utils.*
import javax.inject.*

@Module
class AppModule(
  val appCfg: GtfsLookupServerConfig
) {
  
  constructor(vararg args: String) : this(parseArgs<GtfsLookupServerConfig>(*args))
  
  
  @Provides
  @Singleton
  fun provideWorkerGroup(): NioEventLoopGroup {
    return NioEventLoopGroup(1, DefaultThreadFactory("worker-group", true))
  }
  
  
  @Provides
  fun provideServer(service: GtfsLookupService, cfg: GtfsLookupServerConfig, workerGroup: NioEventLoopGroup): Server {
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
  fun provideServerConfig(): GtfsLookupServerConfig {
    return appCfg
  }
}