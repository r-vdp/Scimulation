package core.graph

/**
 * A class representing undirected graphs.
 * Created by Ramses de Norre
 * Date: 27/10/11
 * Time: 10:32
 */
class UndirectedGraph[V <: Vertex[V], E <: Edge[V]] extends Graph[V, E] {
  private[this] var map: Map[V, Set[E]] = Map.empty

  override def size = map.size

  def contains(vertex: V) = map contains vertex
  def contains(edge: E) = edge forall {
    map.get(_) flatMap (es => Some(es contains edge)) getOrElse false
  }

  protected def addVertexImpl(vertex: V) {
    map += (vertex -> Set.empty)
  }

  protected def addEdgeImpl(edge: E) {
    edge foreach { vertex =>
      map += (vertex -> (map(vertex) + edge))
      vertex addNeighbour (edge other vertex).get
    }
  }

  protected def removeEdgeImpl(edge: E) {
    edge foreach { v => map += (v -> (map(v) filter (edge !=)))}
  }

  protected def removeVertexImpl(vertex: V) {
    map(vertex) foreach removeEdge
    map -= vertex
  }

  /**
   * Retrieve set of all neighbours
   */
  def neighbours(vertex: V) =
    if (contains(vertex)) {
      ((map(vertex) map (_ other vertex))
        withFilter (_.isDefined)) map (_.get)
    } else {
      Set.empty
    }

  def neighbourEdges(vertex: V) = map(vertex)

  protected[graph] def vertices = map.keySet

  protected[graph] def edges = map.values.flatten.toSet

  def someVertex = map.keys.head

  override def toString =
    this.getClass.getCanonicalName + "\n" + Graph.stringBuilder(map)
}
