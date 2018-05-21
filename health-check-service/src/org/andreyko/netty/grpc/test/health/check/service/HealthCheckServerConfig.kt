package org.andreyko.netty.grpc.test.health.check.service

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class HealthCheckServerConfig {
  @JsonProperty("port")
  var port: Int = 8080
}