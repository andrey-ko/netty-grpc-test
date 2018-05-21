package org.andreyko.netty.grpc.test.health.check.service

import org.junit.*
import org.junit.Assert.*

class AppModuleTests{
  
  @Test
  fun argsTest() {
    val appModule = AppModule(
      "--port", "80",
      "--gtfs-lookup.port", "81",
      "--gtfs-lookup.host", "localhost",
      "--organization.port", "82",
      "--organization.host", "localhost"
    )
    
    appModule.provideHealthCheckServerConfig().apply {
      assertEquals(80, port)
    }
  
    appModule.provideGtfsLookupClientConfig().apply {
      assertEquals(81, port)
      assertEquals("localhost", host)
    }
  
    appModule.provideOrganizationClientConfig().apply {
      assertEquals(82, port)
      assertEquals("localhost", host)
    }
  
  }
  
}