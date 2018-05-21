package org.andreyko.netty.grpc.test.gtfs.lookup.service

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class GtfsLookupClientConfig {
  @JsonProperty("host")
  var host: String = "gtfs-lookup-service"
  
  @JsonProperty("port")
  var port: Int = 8080
}