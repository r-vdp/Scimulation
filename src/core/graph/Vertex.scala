package core.graph

import persistence.VertexBuilder
import xml.NodeBuffer

/**
 * Class representing abstract vertices in a graph.
 * Make sure to properly override equals and hashcode,
 * corrupt implementations will cause the Graph class to malfunction.
 *
 * Created by Ramses de Norre
 * Date: 27/10/11
 * Time: 10:32
 */
abstract class Vertex(val id: String) {

  var params: Map[String, Any]

  override def equals(that: Any) = that match {
    case Vertex(`id`) => true
    case _ => false
  }

  override val hashCode = id.hashCode

  def toXML =
    <vertex>
      <class>{getClass.getCanonicalName}</class>
      <id>{id}</id>
      {paramXML}
    </vertex>

  def paramXML = {
    val out = new NodeBuffer
    params foreach { case (key, value) =>
      out += <attr> <name>{key}</name> <value>{value}</value> </attr>
    }
    out
  }
}

object Vertex {
  def unapply(v: Vertex) = Some(v.id)

  def fromXML[V <: Vertex](node: xml.Node): V = {
    val builder = new VertexBuilder
    val vertexClass = (node \ "class").text
    val id = (node \ "id").text
    val attrs = (node \ "attr")
    var params = Map.empty[String, Any]
    attrs foreach {n => params += ((n\"name").text -> (n\"value").text)}

    builder.create[V](vertexClass, id, params)
  }
}
