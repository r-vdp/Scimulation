package core.graph

import scala.collection.Seq
import scala.collection.Iterator

/**
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */

abstract case class Edge[V <: Vertex](left: V, right: V)
  extends Seq[V] with Ordered[Edge[V]] {

  override val length = 2
  val weight: Double

  /**
   * Return the other node, or None if the given node is not contained in
   * this edge
   */
  def other(vertex: V) = vertex match {
    case `left`  => Some(right)
    case `right` => Some(left)
    case _       => None
  }

  override def apply(idx: Int) = idx match {
    case 0 => left
    case 1 => right
    case _ => throw new IndexOutOfBoundsException
  }

  override def iterator = new Iterator[V] {
    private var idx = 0

    def hasNext = idx < 2

    def next() = {
      idx += 1
      Edge.this(idx - 1)
    }
  }

  def compare(that: Edge[V]) = this.weight compare that.weight

}
