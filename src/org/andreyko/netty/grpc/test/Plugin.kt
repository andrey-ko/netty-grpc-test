package org.andreyko.netty.grpc.test

import com.google.protobuf.*
import com.google.protobuf.compiler.*
import io.grpc.*
import io.grpc.protobuf.*
import java.io.*
import java.nio.file.*
import java.util.concurrent.*

object Plugin {
  
  var getStatusMethod: MethodDescriptor<Empty, Status>? = null
  private fun getStatusMethodHelper(): MethodDescriptor<Empty, Status> {
    val getStatusMethod = getStatusMethod
    if(getStatusMethod !== null) return getStatusMethod
    
    synchronized(GtfsLookupGrpc::class.java){
      val res = MethodDescriptor.newBuilder<Empty, Status>()
        .setType(MethodDescriptor.MethodType.UNARY)
        .setFullMethodName(MethodDescriptor.generateFullMethodName("com.satori.scbe.gtfs.lookup.GtfsLookup", "status"))
        .setRequestMarshaller(ProtoUtils.marshaller(Empty.getDefaultInstance()))
        .setResponseMarshaller(ProtoUtils.marshaller(Status.getDefaultInstance()))
        //.setSampledToLocalTracing(true)
        .setSchemaDescriptor()
        .build()
      this.getStatusMethod = res
      return res
    }
    
    
    
    
    if ((getStatusMethod = GtfsLookupGrpc.getStatusMethod) == null) {
      synchronized(GtfsLookupGrpc::class.java) {
        if ((getStatusMethod = GtfsLookupGrpc.getStatusMethod) == null) {
          getStatusMethod = io.grpc.
            .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
            .setFullMethodName(generateFullMethodName(
              "com.satori.scbe.gtfs.lookup.GtfsLookup", "status"))
            .setSampledToLocalTracing(true)
            .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.google.protobuf.Empty.getDefaultInstance()))
            .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              org.andreyko.netty.grpc.test.Status.getDefaultInstance()))
            .setSchemaDescriptor(GtfsLookupMethodDescriptorSupplier("status"))
            .build()
          GtfsLookupGrpc.getStatusMethod = getStatusMethod
        }
      }
    }
    return getStatusMethod
  }
  
  @JvmStatic
  fun main(vararg args: String) {
    val fds = File("grpc/.out/grpc.pb").inputStream().use {istream->
      DescriptorProtos.FileDescriptorSet.parseFrom(istream)
    }
    for (fd in fds.fileList) {
      
      for(sd in fd.serviceList){
        println ("service name: ${sd.name}")
        for(md in sd.methodList){
          println ("  method name: ${md.name}")
        }
      }
      
    }
    
    //println ("message definitions:\n ${fds}")
  }
}