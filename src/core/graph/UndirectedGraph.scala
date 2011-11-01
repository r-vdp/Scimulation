package core.graph

import traversal.{SimpleGraphTraverser, GraphTraverser}

/**
 * A class representing undirected graphs.
 * Created by Ramses de Norre
 * Date: 27/10/11
 * Time: 10:32
 */
class UndirectedGraph[V <: Vertex, E <: Edge[V]] extends Graph[V, E] {
  private[this] var map: Map[V, Set[E]] = Map.empty

  override def size = map.size

  def contains(vertex: V) = map contains vertex
  def contains(edge: E) = edge forall {
    map.get(_) flatMap (es => Some(es contains edge)) getOrElse false
  }

  def addVertex(vertex: V, fire: Boolean = true) = {
    if(!contains(vertex)) {
      map += (vertex -> Set.empty)
      fireIf(fire)
      true
    } else {
      false
    }
  }

  def addEdge(edge: E, fire: Boolean = true) = {
    if (isLegal(edge)) {
      edge foreach { vertex => map += (vertex -> (map(vertex) + edge))}
      fireIf(fire)
      true
    } else {
      false
    }
  }

  def removeEdge(edge: E) {
    if (contains(edge)) {
      edge foreach { v => map += (v -> (map(v) filter (edge !=)))}
    }
  }

  def removeVertex(vertex: V) {
    if (contains(vertex)) {
      map(vertex) foreach removeEdge
      map -= vertex
      assert(!contains(vertex))
    }
  }

  /**
   * Retrieve set of all neighbours
   */
  def neighbours(vertex: V) =
    if (contains(vertex)) {
      ((map(vertex) map (_.other(vertex)))
        withFilter (_.isDefined)) map (_.get)
    } else {
      Set.empty
    }

  def neighbourEdges(vertex: V) = map(vertex)

  protected[graph] def vertices = map.keySet

  def someVertex = map.keys.head

  def setTraverser(t: GraphTraverser[V, E]) {
    traverser = t
  }

  protected var traverser: GraphTraverser[V, E] =
    new SimpleGraphTraverser(this)

  override def toString = Graph.stringBuilder(map)
}
