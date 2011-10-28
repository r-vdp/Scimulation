package core.graph

import scala.collection.Seq
import scala.collection.Iterator

/**
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */

abstract case class Edge[N <: Node](left: N, right: N) extends Seq[N] {
  override val length = 2

  /**
   * Return the other node, or None if the given node is not contained in
   * this edge
   */
  def other(node: N) = node match {
    case `left`  => Some(right)
    case `right` => Some(left)
    case _       => None
  }

  override def apply(idx: Int) = idx match {
    case 0 => left
    case 1 => right
    case _ => throw new IndexOutOfBoundsException
  }

  override def iterator = new Iterator[N] {
    private var idx = 0;

    def hasNext = idx < 2

    def next() = {
      idx += 1
      Edge.this(idx - 1)
    }
  }
}
