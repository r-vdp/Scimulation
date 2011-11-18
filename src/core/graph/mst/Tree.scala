package core.graph.mst

import scala.collection.{Iterable, Iterator}

/**
 * Created by Ramses de Norre
 * Date: 28/10/11
 * Time: 16:27
 */

@deprecated("Unused", "")
sealed abstract class Tree[+A] extends Iterable[A]

@deprecated("Unused", "")
case class Leaf[+A](a: A) extends Tree[A] {
  def iterator = Iterator.single(a)
}

/**
 * A Node in a Tree can contain two or more subtrees, the iterator iterates
 * the children in the order in which they are supplied to the constructor.
 */
@deprecated("Unused", "")
case class Node[+A](c1: Tree[A], c2: Tree[A], cs: Tree[A]*) extends Tree[A] {

  def iterator = new Iterator[A] {
    // Store the current iterator and the children left to be seen
    private[this] var curIt = c1.iterator
    private[this] var remChil = c2 :: cs.toList

    private[this] def currentIt = {
      if (!curIt.hasNext) {
        if (!remChil.isEmpty) {
          curIt = remChil.head.iterator
          remChil = remChil.tail
        } else {
          curIt = Iterator.empty
        }
      }
      curIt
    }

    def hasNext = currentIt.hasNext
    def next() = currentIt.next()
  }
}

@deprecated("Unused", "")
case object Empty extends Tree[Nothing] {
  def iterator = Iterator.empty
}
