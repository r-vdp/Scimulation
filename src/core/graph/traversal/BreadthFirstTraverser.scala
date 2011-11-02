package core.graph.traversal

import core.graph.{DirectedGraph, Edge, Vertex}


/**
 * Created by Ramses de Norre
 * Date: 29/10/11
 * Time: 17:47
 */
@deprecated("Useless?", "0")
class BreadthFirstTraverser[V <: Vertex, E <: Edge[V]] (graph: DirectedGraph[V, E])
  extends GraphTraverser[V, E](graph) {

  def foreach[U](f: (V) => U) {
    traverse(f)(graph.someVertex)
  }

  private def traverse[U](f: (V) => U)(v: V) {
    f(v)
    graph.neighbours(v) foreach traverse(f)
  }

  def test[U](f: (V) => U)(v: V) {
    var list = (graph.neighbours(v) filter (v!=)).toList
  }
}
