package core.graph

/**
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */
class BaseVertex(id: String,
                 override var params: Map[String, Any] = Map.empty)
  extends Vertex(id) {

  def getName: Option[Any] = params.get("name")
}

object BaseVertex {
  def apply(id: String, params: Map[String, Any] = Map.empty) =
    new BaseVertex(id, params)
}
