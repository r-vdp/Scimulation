package core.graph

/**
 * Trait representing abstract vertices in a graph.
 * Make sure to properly override equals and hashcode,
 * corrupt implementations will cause the Graph class to malfunction.
 *
 * Created by Ramses de Norre
 * Date: 27/10/11
 * Time: 10:32
 */
trait Vertex {
  override def equals(that: Any): Boolean
  override def hashCode: Int
}
