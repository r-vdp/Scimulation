package core.graph

import core.Observable
import traversal.{SimpleGraphTraverser, GraphTraverser}

/**
 * Created by Ramses de Norre
 * Date: 27/10/11
 * Time: 10:32
 */
class Graph[V <: Vertex, E <: Edge[V]] extends Observable with Traversable[V] {
  private[graph] var map: Map[V, Set[E]] = Map.empty

  override def size = map.size

  def contains = map.contains _

  /**
   * Can this edge be a part of this graph?
   * True iff both the vertices referenced by edge are contained in this graph
   */
  val isLegal = (_:E) forall contains

  def addVertex(vertex: V) {
    if(!contains(vertex)) {
      map += (vertex -> Set.empty)
    }
  }

  def addVertices(vertices:Seq[V]) {
    vertices foreach addVertex
  }

  def addEdge(edge: E) {
    if (isLegal(edge)) {
      edge foreach { vertex =>
        map += (vertex -> (map(vertex) + edge))
      }
    }
  }

  def addEdges(edges: Seq[E]) {
    edges foreach addEdge
  }

  def removeEdge(edge: E) {
    if (isLegal(edge)) {
      edge foreach { v =>
          val rest = map(v) filter (edge !=)
          if (rest.isEmpty) {
            map -= v
          } else {
            map += (v -> rest)
          }
      }
    }
  }

  def removeVertex(vertex: V) {
    if (contains(vertex)) {
      map(vertex) foreach removeEdge
      assert(!contains(vertex))
    } else {
      throw new IllegalArgumentException(
        "Vertex (" + vertex + ") does not exist in graph!")
    }
  }

  /**
   * Retrieve set of all neighbours
   */
  def neighbours(vertex: V): Set[V] =
    if (contains(vertex)) {
      ((map(vertex) map (_.other(vertex)))
        withFilter (_.isDefined)) map (_.get)
    } else {
      Set.empty
    }

  def adjacentVertices(vertex: V): Set[E] = map(vertex)

  /**
   * Retrieve some vertex that is part of this graph,
   * used to be able to get an entrance point into the graph.
   */
  def someVertex = map.keys.head

  override def toString = {
    for (vertex <- map.keys;
         edge <- map(vertex)
    ) yield edge.toString()
  } mkString "\n"

  def foreach[U](f: (V) => U) {
    foreachImpl(f)
  }

  def foreach[U](f: (V) => U, traverser: GraphTraverser[V, E]) {
    foreachImpl(f)(traverser)
  }

  def traverser_=(t: GraphTraverser[V, E]) {
    traverser = t
  }

  private[this] implicit var traverser: GraphTraverser[V, E] =
    new SimpleGraphTraverser(this)

  private def foreachImpl[U](f: (V) => U)
                            (implicit traverser: GraphTraverser[V, E]) {
    traverser foreach f
  }

  private[graph] def vertices = map.keys.toList
}
