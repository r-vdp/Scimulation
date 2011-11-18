package core.graph

/**
 * A class representing undirected graphs.
 * The graph is made by automatically adding the reverse of each edge that is
 * added to the graph as well.
 *
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
   * Retrieve set of all neighbours, Option monad madness!
   */
  def neighbours(vertex: V) =
    ((map get vertex) flatMap
      {es => Some((es map (_ other vertex)).flatten)}
    ).flatten[V]

  def neighbourEdges(vertex: V) = (map get vertex).flatten[E]

  def vertices = map.keySet

  def edges = map.values.flatten[E]

  def someVertex = map.keys.head

  override def toString =
    this.getClass.getCanonicalName + "\n" + Graph.stringBuilder(map)

  def deepCopy: UndirectedGraph[V, E] = {
    val graph = new UndirectedGraph[V, E]
    graph.addVertices(vertices)
    graph.addEdges(edges)
    graph
  }
}
