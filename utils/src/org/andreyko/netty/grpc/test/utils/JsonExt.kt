package org.andreyko.netty.grpc.test.utils

import com.fasterxml.jackson.databind.node.*
import com.satori.libs.common.kotlin.json.*
import java.util.*

fun JsonObjectBuilderScope.merge(path: String, value: String) {
  var n = objectNode
  val tok = StringTokenizer(path, ".")
  while (tok.hasMoreTokens()) {
    val p = tok.nextToken()
    val next = n.get(p)
    if(next == null || next !is ObjectNode){
      var t = p
      while(tok.hasMoreTokens()){
        n = jsonObject().also {
          n.replace(t, it)
        }
        t = tok.nextToken()
      }
      n.put(t, value)
      return
    }
    n = next
  }
}

inline fun<reified T> parseArgs(vararg args:String): T {
  val objectNode = jsonObject {
    val itor = args.iterator()
    while (true) {
      val name = if (itor.hasNext()) itor.next() else break
      val value = if (itor.hasNext()) itor.next() else throw Exception("argument value is missing")
      if (!name.startsWith("--")) throw Exception("argument '$name' should start with '--'")
      merge(name.removePrefix("--"), value)
    }
  }
  return DefaultJsonContext.jsonTreeToValue(objectNode)
}