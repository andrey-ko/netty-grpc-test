package org.andreyko.netty.grpc.test.organization.service;

import com.google.protobuf.*
import io.grpc.stub.*
import org.slf4j.*
import javax.inject.*

class OrganizationService @Inject constructor() : OrganizationGrpc.OrganizationImplBase() {
  val log = LoggerFactory.getLogger(javaClass)
  
  override fun status(request: Empty?, responseObserver: StreamObserver<Status>) {
    log.info("status request received")
    responseObserver.onNext(Status.newBuilder().apply {
      name = MetaInfo.project
      version = MetaInfo.version
      sha = MetaInfo.gitSha
      api = "v0"
    }.build())
    responseObserver.onCompleted()
  }
}