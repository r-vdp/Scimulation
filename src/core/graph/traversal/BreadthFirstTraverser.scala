package core.graph.traversal

import core.graph.{DirectedGraph, Edge, Vertex}

/**
 * Created by Ramses de Norre
 * Date: 29/10/11
 * Time: 17:47
 */
class BreadthFirstTraverser[V <: Vertex, E <: Edge[V]]
  (graph: DirectedGraph[V, E]) extends GraphTraverser[V, E](graph) {

  private[this] var visited: Set[V] = Set.empty

  def foreach[U](f: (V) => U) {
    traverse(f)(graph.someVertex)
  }

  private def traverse[U](f: (V) => U)(v: V) {
    if (!visited(v)) {
      visited += v
      f(v)
      (graph neighbours v) foreach traverse(f)
    }
  }
}
