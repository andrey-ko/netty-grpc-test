package org.andreyko.netty.grpc.test.organization.service

import dagger.*
import io.grpc.*
import javax.inject.*

@Singleton
@Component(modules = arrayOf(
  AppModule::class
))
interface AppComponent {
  fun newServer(): Server
}
