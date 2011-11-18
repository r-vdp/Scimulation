package core.graph.traversal

import core.graph.{Graph, Edge, Vertex}

/**
 * Simple traverser that iterates all vertices in no particular order.
 * Works well with multi-partite graphs as well.
 *
 * Created by Ramses de Norre
 * Date: 28/10/11
 * Time: 16:07
 */
private[graph] class SimpleGraphTraverser[V <: Vertex[V], E <: Edge[V]]
  (graph: Graph[V, E])
    extends GraphTraverser[V, E](graph) {

  def foreach[U](f: (V) => U) {
    graph.vertices foreach f
  }
}
