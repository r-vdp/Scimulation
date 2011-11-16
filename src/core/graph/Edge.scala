package core.graph

import core.persistence.EdgeBuilder

/**
 * Directed edge which connects two vertices and has some weight.
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */
abstract class Edge[V <: Vertex[V]]
  extends IndexedSeq[V] with Ordered[Edge[V]] {

  val from: V
  val to: V
  val weight: Double

  type This <: Edge[V]

  override val length = 2

  /**
   * Return the other node, or None if the given node is not contained in
   * this edge
   */
  def other(vertex: V) = vertex match {
    case `from` => Some(to)
    case `to`   => Some(from)
    case _      => None
  }

  override def apply(idx: Int) = idx match {
    case 0 => from
    case 1 => to
    case _ => throw new IndexOutOfBoundsException
  }

  override def reverse: This = construct(to, from, weight)

  protected def construct(from: V, to: V, weight: Double): This

  def compare(that: Edge[V]) = this.weight compare that.weight

  /**
   * Override equals from Seq because we want weight to matter as well.
   */
  override def equals(that: Any) = that match {
    case Edge(`from`, `to`, `weight`) => true
    case _ => false
  }

  override def hashCode =
    from.hashCode + (31 * to.hashCode + (31 * weight.toString.hashCode))

  def toXML =
    <edge>
      <class>{getClass.getCanonicalName}</class>
      <from>{from.id}</from>
      <to>{to.id}</to>
      <weight>{weight}</weight>
    </edge>
}

/**
 * An extractor to pattern match on general edges,
 * the loose typing of unapply complies with type erasure.
 */
object Edge {
  def unapply(edge: Edge[_]): Option[(Any, Any, Double)] =
    Some((edge.from, edge.to, edge.weight))

  def fromXML[V <: Vertex[V], E <: Edge[V]](vertices: Map[String, V])
                                        (node: xml.Node): E = {
    val builder = new EdgeBuilder
    val edgeClass = (node \ "class").text
    val from = (node \ "from").text
    val to = (node \ "to").text
    val weight = (node \ "weight").text.toDouble

    builder.create[V, E](edgeClass, vertices(from), vertices(to), weight)
  }
}
