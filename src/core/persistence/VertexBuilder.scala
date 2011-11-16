package core.persistence

import core.graph.Vertex
import scala.collection.mutable.Map

/**
 * Created by Ramses de Norre
 * Date: 03/11/11
 * Time: 21:08
 */
class VertexBuilder extends GenericBuilder {
  def create[V <: Vertex[V]]
      (vertexClass: String, id: String, params: Map[String, Any]): V = {
    New[V](vertexClass)(id, params)
  }
}
