package org.andreyko.netty.grpc.test.health.check.service

import com.fasterxml.jackson.annotation.*
import org.andreyko.netty.grpc.test.gtfs.lookup.service.*
import org.andreyko.netty.grpc.test.organization.service.*

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class AppConfig {
  @JsonProperty("port")
  var port: Int = 8080
  
  @JsonProperty("gtfs-lookup")
  var gtfsLookup: GtfsLookupClientConfig = GtfsLookupClientConfig()
  
  @JsonProperty("organization")
  var organization: OrganizationClientConfig = OrganizationClientConfig()
}