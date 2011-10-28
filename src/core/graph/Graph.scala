package core.graph

import core.Observable

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
      edge foreach {
        vertex => map += (vertex -> (map(vertex) + edge))
      }
    }
  }

  def addEdges(edges: Seq[E]) {
    edges foreach addEdge
  }

  def removeEdge(edge: E) {
    if (isLegal(edge)) {
      edge foreach {
        vertex =>
          val rest = map(vertex) filter (edge !=)
          if (rest.isEmpty) {
            map -= vertex
          } else {
            map += (vertex -> rest)
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
  def getNeighbours(vertex: V): Set[V] =
    if (contains(vertex)) {
      (map(vertex) withFilter {_.other(vertex).isDefined}) map (_.other(vertex).get)
    } else {
      Set.empty
    }

  override def toString() = {
    for (vertex <- map.keys;
         edge <- map(vertex)
    ) yield edge.toString()
  } mkString "\n"

  def foreach[U](f: (V) => U) {
    foreachImpl(f)
  }

  implicit val traverser = new GraphTraverser(this)
  private def foreachImpl[U](f: (V) => U)
                            (implicit traverser: GraphTraverser[V,E]) {
    traverser foreach f
  }

  //todo remove, for testing only
  @deprecated
  def vertices = map.keys.toList
}

trait Vertex
