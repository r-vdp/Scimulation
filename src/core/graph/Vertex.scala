package core.graph

import xml.NodeBuffer
import scala.collection.mutable.Map
import core.persistence.VertexBuilder

/**
 * Trait representing abstract vertices in a graph.
 * Make sure to properly override equals and hashcode,
 * corrupt implementations will cause the Graph class to malfunction.
 *
 * The type parameter V should be the subclass type itself,
 * it is needed to be able to reference a subclass type in the methods
 * dealing with neighbours.
 * We express this using the explicitely typed self-reference,
 * this construction allows for a recursive type constructor.
 *
 * Created by Ramses de Norre
 * Date: 27/10/11
 * Time: 10:32
 */
trait Vertex[V <: Vertex[V]] { _: V =>

  /**
   * Lazy vals to allow for correct initialisation order,
   * failure to initialise these properly will result in an exception.
   * These are a work-around for the problem of corrupt initialisation of
   * abstract vals in traits and avoid the need for early initialisation in
   * subclasses.
   */
  lazy val id: String = {new UninitializedError; null}
  lazy val params: Map[String, Any] = {new UninitializedError; null}

  override def equals(that: Any) = that match {
    case Vertex(`id`) => true
    case _ => false
  }

  override val hashCode = id.hashCode

  private[this] var neighbourSet: Set[V] = Set.empty

  private[graph] def addNeighbour(vertices: V*) {
    neighbourSet ++= vertices
  }

  private[graph] def removeNeighbour(vertices: V*) {
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
  /**
   * Extractor to allow pattern matching on general vertices.
   * Be careful with subclasses!
   */
  def unapply(v: Vertex[_]) = Some(v.id)

  def fromXML[V <: Vertex[V]](node: xml.Node): V = {
    val vertexClass = (node \ "class").text
    val id = (node \ "id").text
    val attrs = (node \ "attr")
    var params = Map.empty[String, Any]
    attrs foreach {n => params += ((n\"name").text -> (n\"value").text)}

    (new VertexBuilder).create[V](vertexClass, id, params)
  }
}
