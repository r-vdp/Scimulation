package core.graph

import persistence.VertexBuilder
import xml.NodeBuffer
import scala.collection.mutable.Map

/**
 * Trait representing abstract vertices in a graph.
 * Make sure to properly override equals and hashcode,
 * corrupt implementations will cause the Graph class to malfunction.
 *
 * The type parameter V should be the subclass type itself,
 * it is needed to be able to reference a subclass type in the methods
 * dealing with neighbours.
 *
 * Created by Ramses de Norre
 * Date: 27/10/11
 * Time: 10:32
 */
trait Vertex[V <: Vertex[V]] { self: V =>

  lazy val id: String = {new UninitializedError; null}
  lazy val params: Map[String, Any] = {new UninitializedError; null}

  override def equals(that: Any) = that match {
    case Vertex(`id`) => true
    case _ => false
  }

  override val hashCode = id.hashCode

  private[this] var neighbourSet: Set[V] = Set.empty

  def addNeighbour(vertices: V*) {
    neighbourSet ++= vertices
  }

  def removeNeighbour(vertices: V*) {
    neighbourSet = neighbourSet filterNot (neighbours contains)
  }

  def neighbours: Seq[V] = Nil ++ neighbourSet

  def toXML =
    <vertex>
      <class>{getClass.getCanonicalName}</class>
      <id>{id}</id>
      {paramXML}
    </vertex>

  def paramXML = {
    val out = new NodeBuffer
    params foreach { case (key, value) =>
      out +=
        <attr>
          <name>{key}</name>
          <value>{value}</value>
        </attr>
    }
    out
  }
}

object Vertex {
  def unapply(v: Vertex[_]) = Some(v.id)

  def fromXML[V <: Vertex[V]](node: xml.Node): V = {
    val builder = new VertexBuilder
    val vertexClass = (node \ "class").text
    val id = (node \ "id").text
    val attrs = (node \ "attr")
    var params = Map.empty[String, Any]
    attrs foreach {n => params += ((n\"name").text -> (n\"value").text)}

    builder.create[V](vertexClass, id, params)
  }
}
