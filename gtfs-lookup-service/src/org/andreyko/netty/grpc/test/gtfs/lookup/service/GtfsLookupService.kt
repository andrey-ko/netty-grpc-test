package org.andreyko.netty.grpc.test.gtfs.lookup.service

import com.google.protobuf.*
import io.grpc.stub.*

class GtfsLookupService : GtfsLookupGrpc.GtfsLookupImplBase() {
  
  override fun status(request: Empty?, responseObserver: StreamObserver<Status>?) {
    super.status(request, responseObserver)
  }
  
  override fun info(request: Empty, responseObserver: StreamObserver<Info>) {
    responseObserver.onNext(Info.newBuilder().apply {
      name = MetaInfo.project
      version = MetaInfo.version
      api = MetaInfo.apiVersion
      sha = MetaInfo.gitSha
    }.build())
    responseObserver.onCompleted()
  }
}