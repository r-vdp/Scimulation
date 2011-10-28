package core.graph

import mst.{MSTFinder, Tree}
import scala.collection.Traversable
/**
 * Created by Ramses de Norre
 * Date: 28/10/11
 * Time: 16:07
 */

private[graph] class GraphTraverser[V <: Vertex ,E <: Edge[V]]
    (private[this] val graph: Graph[V,E])
      extends Traversable[V] {

  lazy val mst: Tree[V] = MSTFinder.find(graph)

  def foreach[U](f: (V) => U) {
    mst foreach f
  }
}
