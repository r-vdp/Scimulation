package core.graph.traversal

import core.graph.{Edge, Graph, Vertex}

/**
 * Created by Ramses de Norre
 * Date: 29/10/11
 * Time: 17:21
 */

abstract class GraphTraverser[V <: Vertex, E <: Edge[V]]
  (protected  val graph: Graph[V, E])
    extends Traversable[V]