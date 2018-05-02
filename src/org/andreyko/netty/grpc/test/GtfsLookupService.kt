package org.andreyko.netty.grpc.test

import com.google.protobuf.*
import io.grpc.stub.*

class GtfsLookupService : GtfsLookupGrpc.GtfsLookupImplBase() {
  
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