package org.andreyko.netty.grpc.test

import io.grpc.stub.*

class GtfsLookupService : GtfsLookupGrpc.GtfsLookupImplBase() {
  
  override fun ping(request: PingRequest, responseObserver: StreamObserver<PingReply>) {
    val reply = PingReply.newBuilder().apply {
      message = request.message
    }.build()
    responseObserver.onNext(reply)
    responseObserver.onCompleted()
  }
}