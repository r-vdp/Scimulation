package core.graph

import scala.collection.Seq
import scala.collection.Iterator

/**
 * Directed edge which connects two vertices and has some weight.
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */
abstract class Edge[V <: Vertex] extends Seq[V] with Ordered[Edge[V]] {

  val from: V
  val to: V
  val weight: Double

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

  override def reverse = construct(to, from, weight)

  protected def construct(from: V, to: V, weight: Double): Edge[V]

  override def iterator = Iterator(from, to)

  def compare(that: Edge[V]) = this.weight compare that.weight

  /**
   * Override equals from Seq because we want weight to matter as well
   */
  override def equals(that: Any) = that match {
    case Edge(`from`, `to`, `weight`) => true
    case _ => false
  }

  override def hashCode =
    from.hashCode + (31 * to.hashCode + (31 * weight.toString.hashCode))
}

/**
 * An extractor to pattern match on general edges
 */
object Edge {
  def unapply[V <: Vertex](edge: Edge[V]): Option[(V, V, Double)] = {
    Some((edge.from, edge.to, edge.weight))
  }
}
