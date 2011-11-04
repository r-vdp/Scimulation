package core.graph

import traversal.{GraphTraverser, SimpleGraphTraverser}


/**
 * A class representing directed graphs.
 * Created by Ramses de Norre
 * Date: 31/10/11
 * Time: 13:43
 */
class DirectedGraph[V <: Vertex, E <: Edge[V]] extends Graph[V, E] {
  private[this] var map: Map[V, Set[E]] = Map.empty

  override def size = map.size

  def contains(vertex: V) = map contains vertex
  def contains(edge: E) =
    (map get edge.from) flatMap (es => Some(es contains edge)) getOrElse false

  def addVertex(vertex: V, fire: Boolean) = {
    if(!contains(vertex)) {
      map += (vertex -> Set.empty)
      fireIf(fire)
      true
    } else {
      false
    }
  }

  def addEdge(edge: E, fire: Boolean)= {
    if (isLegal(edge)) {
      map += (edge.from -> (map(edge.from) + edge))
      fireIf(fire)
      true
    } else {
      false
    }
  }

  def removeEdge(edge: E) {
    if (contains(edge)) {
      val from = edge.from
      map += (from -> (map(from) filter (edge !=)))
    }
  }

  def removeVertex(vertex: V) {
    if (contains(vertex)) {
      map(vertex) foreach removeEdge
      map foreach { case (v, es) =>
        map += (v -> (es filterNot (_.contains(vertex))))
      }
      map -= vertex
    }
  }

  def neighbours(vertex: V) = {
    if (contains(vertex)) {
      map(vertex) map (_.to)
    } else {
      Set.empty
    }
  }

  def neighbourEdges(vertex: V) = map(vertex)

  protected[graph] def vertices = map.keySet

  protected[graph] def edges = map.values.flatten.toSet

  def someVertex = map.keys.head

  override def toString = Graph.stringBuilder(map)
}
