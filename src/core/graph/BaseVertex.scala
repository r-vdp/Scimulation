package core.graph

import scala.collection.mutable.Map
import visualize.Color

/**
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */
class BaseVertex(inId: String, inMap: Map[String, Any] = Map.empty)
  extends Vertex[BaseVertex] with Color {

  override lazy val id = inId
  override lazy val params = inMap

  def getName: Option[Any] = params.get("name")

  override val color: String = "#ffffff"

  override val toString = "BaseVertex(" + id + ")"
}

object BaseVertex {
  def apply(id: String, params: Map[String, Any] = Map.empty) =
    new BaseVertex(id, params)
}
