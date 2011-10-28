package core.graph.mst

import scala.collection.{Iterable, Iterator}

/**
 * Created by Ramses de Norre
 * Date: 28/10/11
 * Time: 16:27
 */

sealed abstract class Tree[A] extends Iterable[A]

case class Leaf[A](a: A) extends Tree[A] {
  def iterator = new Iterator[A] {
    var hasNext = true

    def next() = {
      hasNext = false
      a
    }
  }
}

case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A] {
  def iterator = new Iterator[A] {
    private[this] val li = left.iterator
    private[this] val lr = right.iterator

    def hasNext = lr.hasNext

    def next() =
      if (li.hasNext) {
        li.next()
      } else {
        lr.next()
      }
  }
}
