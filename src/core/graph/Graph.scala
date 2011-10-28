package core.graph

import core.Observable

/**
 * Created by Ramses de Norre
 * Date: 27/10/11
 * Time: 10:32
 */
class Graph[N <: Node, E <: Edge[N]] extends Observable with Traversable[N] {
  private[graph] var map: Map[N, Set[E]] = Map.empty

  def size = map.size

  def contains = map.contains _

  /**
   * Can this edge be a part of this graph?
   * True iff both the nodes referenced by edge are contained in this graph
   */
  val isLegal = (_:E) forall contains

  def addNode(node: N) {
    if(!contains(node)) {
      map += (node -> Set.empty)
    }
  }

  def addNodes(nodes:Seq[N]) {
    nodes foreach addNode
  }

  def addEdge(edge: E) {
    if (isLegal(edge)) {
      edge foreach {
        node => map += (node -> (map(node) + edge))
      }
    }
  }

  def addEdges(edges: Seq[E]) {
    edges foreach addEdge
  }

  def removeEdge(edge: E) {
    if (isLegal(edge)) {
      edge foreach {
        node =>
          val rest = map(node) filter (edge !=)
          if (rest.isEmpty) {
            map -= node
          } else {
            map += (node -> rest)
          }
      }
    }
  }

  def removeNode(node: N) {
    if (contains(node)) {
      map(node) foreach removeEdge
      assert(!contains(node))
    } else {
      throw new IllegalArgumentException(
        "Node (" + node + ") does not exist in graph!")
    }
  }

  /**
   * Retrieve set of all neighbours
   */
  def getNeighbours(node: N): Set[N] =
    if (contains(node)) {
      (map(node) withFilter {_.other(node).isDefined}) map (_.other(node).get)
    } else {
      Set.empty
    }

  override def toString = {
    for (node <- map.keys;
         edge <- map(node)
    ) yield edge.toString()
  } mkString "\n"

  def foreach[U](f: (N) => U) {
    foreachImpl(f)
  }

  implicit val traverser = new GraphTraverser(this)
  private def foreachImpl[U](f: (N) => U)
                            (implicit traverser: GraphTraverser[N]) {
    traverser foreach f
  }
}

trait Node
