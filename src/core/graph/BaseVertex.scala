package core.graph

import scala.collection.mutable.Map
import core.visualize.Color

/**
 * Example implementation of Vertex, used in tests and such.
 *
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */
class BaseVertex(inId: String, inMap: Map[String, Any] = Map.empty)
  extends Vertex[BaseVertex] with Color {

  override lazy val id = inId
  override lazy val params = inMap

  override val color: String = "#ffffff"

  override val toString = "BaseVertex(" + id + ")"
}

object BaseVertex {
  def apply(id: String, params: Map[String, Any] = Map.empty) =
    new BaseVertex(id, params)
}
