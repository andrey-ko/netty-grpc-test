package org.andreyko.netty.grpc.test.health.check.service

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class HealthCheckClientConfig {
  @JsonProperty("host")
  var host: String = "health-check-service"
  
  @JsonProperty("port")
  var port: Int = 8080
}