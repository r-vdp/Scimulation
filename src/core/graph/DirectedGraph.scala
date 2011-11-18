package core.graph

/**
 * A class representing directed graphs.
 *
 * Created by Ramses de Norre
 * Date: 31/10/11
 * Time: 13:43
 */
class DirectedGraph[V <: Vertex[V], E <: Edge[V]] extends Graph[V, E] {

  private[this] var map: Map[V, Set[E]] = Map.empty

  override def size = map.size

  def contains(vertex: V) = map contains vertex

  /**
   * Get the Set of all edges starting on edge.from,
   * if there is such a set, check whether it contains the edge,
   * otherwise return false.
   */
  def contains(edge: E) =
    (map get edge.from) flatMap (es => Some(es contains edge)) getOrElse false

  protected def addVertexImpl(vertex: V) {
    map += (vertex -> Set.empty)
  }

  protected def addEdgeImpl(edge: E) {
    map += (edge.from -> (map(edge.from) + edge))
    edge.from addNeighbour edge.to
  }

  protected def removeEdgeImpl(edge: E) {
    val from = edge.from
    map += (from -> (map(from) filter (edge !=)))
  }

  protected def removeVertexImpl(vertex: V) {
    map(vertex) foreach removeEdge
    map foreach { case (v, es) =>
      map += (v -> (es filterNot (_.contains(vertex))))
    }
    map -= vertex
  }

  def neighbours(vertex: V) =
    ((map get vertex) flatMap {es => Some(es map (_.to))}).flatten[V]

  def neighbourEdges(vertex: V) = (map get vertex).flatten[E]

  def vertices = map.keySet

  def edges = map.values.flatten[E]

  def someVertex = map.keys.head

  override def toString =
    this.getClass.getCanonicalName + "\n" + Graph.stringBuilder(map)

  def deepCopy: DirectedGraph[V, E] = {
    val graph = new DirectedGraph[V, E]
    graph.addVertices(vertices)
    graph.addEdges(edges)
    graph
  }
}
