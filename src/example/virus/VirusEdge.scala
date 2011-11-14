package example.virus

import core.graph._

case class VirusEdge[V <: Vertex[V]](from: V, to: V, weight: Double = 1)
    extends Edge[V] {

  type This = VirusEdge[V]

  protected def construct(from: V, to: V, weight: Double) =
    VirusEdge(from, to, weight)
}
