package example.social
import core.graph.Vertex
import core.graph.Edge

case class SocialEdge[V <: Vertex[V]](from: V, to: V, weight: Double = 1)
    extends Edge[V] {

  type This = SocialEdge[V]

  protected def construct(from: V, to: V, weight: Double) =
    SocialEdge(from, to, weight)
}