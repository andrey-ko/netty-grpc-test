package org.andreyko.netty.grpc.test.health.check.service

import dagger.*
import io.grpc.*
import org.andreyko.netty.grpc.test.gtfs.lookup.service.*
import org.andreyko.netty.grpc.test.organization.service.*
import javax.inject.*

@Singleton
@Component(modules = arrayOf(
  AppModule::class,
  GtfsLookupModule::class,
  OrganizationModule::class
))

interface AppComponent {
  fun newServer(): Server
}
