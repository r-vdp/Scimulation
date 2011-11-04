package core.graph.persistence

import core.graph.{Vertex, Edge}

/**
 * Created by Ramses de Norre
 * Date: 03/11/11
 * Time: 22:49
 */
class EdgeBuilder {
  def create[V <: Vertex, E <: Edge[V]]
      (edgeClass: String, from: V, to: V, weight: Double): E = {
    val edgeConst = Class.forName(edgeClass).getConstructors
    val list = from :: to :: weight :: Nil

    edgeConst(0).newInstance(list map {_.asInstanceOf[AnyRef]}: _*)
      .asInstanceOf[E]
  }
}
