package core.graph.persistence

import core.graph.{Graph, Edge, Vertex}

/**
 * Created by Ramses de Norre
 * Date: 04/11/11
 * Time: 17:41
 */
class GraphBuilder extends GenericBuilder {
  def create[V <: Vertex, E <: Edge[V]](graphClass: String): Graph[V, E] = {
    New[Graph[V, E]](graphClass)()
  }
}
