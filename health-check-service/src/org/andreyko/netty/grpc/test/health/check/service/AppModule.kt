package org.andreyko.netty.grpc.test.health.check.service

import dagger.*
import io.fabric8.kubernetes.client.*
import io.grpc.*
import io.grpc.netty.*
import io.netty.channel.nio.*
import io.netty.util.concurrent.*
import org.andreyko.netty.grpc.test.gtfs.lookup.service.*
import org.andreyko.netty.grpc.test.organization.service.*
import org.andreyko.netty.grpc.test.utils.*
import javax.inject.*

@Module
class AppModule(
  val appCfg: AppConfig
) {
  
  constructor(vararg args: String) : this(parseArgs<AppConfig>(*args))
  
  @Provides
  @Singleton
  fun provideWorkerGroup(): NioEventLoopGroup{
    return NioEventLoopGroup(1, DefaultThreadFactory("worker-group", true))
  }
  
  @Provides
  @Singleton
  fun provideKubeClient(): DefaultKubernetesClient{
    return DefaultKubernetesClient()
  }
  
  
  @Provides
  fun provideHealthCheckServer(service: HealthCheckService, cfg: HealthCheckServerConfig, workerGroup: NioEventLoopGroup): Server {
    return NettyServerBuilder.forPort(cfg.port)
      .executor({runnable->
        runnable.run()
      })
      .workerEventLoopGroup(workerGroup)
      .addService(service)
      .build()
  }

  
  
  @Provides
  fun provideHealthCheckServerConfig(): HealthCheckServerConfig {
    return HealthCheckServerConfig().apply {
      port = appCfg.port
    }
  }
  
  @Provides
  fun provideGtfsLookupClientConfig(): GtfsLookupClientConfig {
    return appCfg.gtfsLookup
  }
  
  @Provides
  fun provideOrganizationClientConfig(): OrganizationClientConfig {
    return appCfg.organization
  }
}