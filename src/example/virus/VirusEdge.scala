package example.virus

import core.graph._

case class VirusEdge[V<:Vertex](from: V, to: V, weight: Double = 1)
    extends Edge[VirusActor] {
  protected def construct(from: V, to: V, weight: Double) =
    VirusEdge(from, to, weight)
}



