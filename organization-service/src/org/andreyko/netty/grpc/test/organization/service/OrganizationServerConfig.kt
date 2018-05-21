package org.andreyko.netty.grpc.test.organization.service

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class OrganizationServerConfig {
  @JsonProperty("port")
  var port: Int = 8080
}