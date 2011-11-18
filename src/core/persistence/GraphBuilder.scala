package core.persistence

import core.graph.{Graph, Edge, Vertex}

/**
 * Builder to construct graphs from a string.
 *
 * Created by Ramses de Norre
 * Date: 04/11/11
 * Time: 17:41
 */
class GraphBuilder extends GenericBuilder {
  def create[V <: Vertex[V], E <: Edge[V]](graphClass: String): Graph[V, E] = {
    New[Graph[V, E]](graphClass)()
  }
}
