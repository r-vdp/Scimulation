package core.graph

import scala.collection.mutable.Map

/**
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */
class BaseVertex(id: String,
                 override val params: Map[String, Any] = Map.empty)
  extends Vertex(id) {

  def getName: Option[Any] = params.get("name")

  override def toString = "BaseVertex(" + id + ")"
}

object BaseVertex {
  def apply(id: String, params: Map[String, Any] = Map.empty) =
    new BaseVertex(id, params)
}
