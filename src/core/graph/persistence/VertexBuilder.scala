package core.graph.persistence

import core.graph.Vertex

/**
 * Created by Ramses de Norre
 * Date: 03/11/11
 * Time: 21:08
 */
class VertexBuilder {
  def create[V <: Vertex]
      (vertexClass: String, id: String, params: Map[String, Any]): V = {
    val vertexConst = Class.forName(vertexClass).getConstructors
    val list = id :: params :: Nil

    vertexConst(0).newInstance(list map {_.asInstanceOf[AnyRef]}: _*)
      .asInstanceOf[V]
  }
}
