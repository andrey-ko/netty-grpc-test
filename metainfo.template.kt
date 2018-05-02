// auto generated
// don't modify
package ${project.pckg}

object ${model.className} {
  val version = "${project.version}"
  val project = "${project.archivesBaseName}"
  val apiVersion = "${project.apiVersion}"
  <% if(model.sha != null) { %>
  val gitSha:String? = "${model.sha}"
  <% } else { %>
  val gitSha:String? = null
  <% } %>
  <% if(model.tags != null && model.tags.size() > 0) { %>
  val gitTags:Array<String>? = arrayOf(
    <%  for (def t in model.tags){ %>"${t}" <% if(t != model.tags.last()) { %>, <% }} %>
  )
  <% } else {%>
  val gitTags:Array<String>? = null
  <% } %>

  override fun toString(): String {
    val sb = StringBuilder()

    sb.append(project)
    sb.append(":")
    sb.append(version)
    <% if(model.sha != null) { %>
    sb.append("(")
    sb.append(gitSha)
    sb.append(")")
    <% } %>
    return sb.toString()
  }
}
