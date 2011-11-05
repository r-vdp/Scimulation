package example.virus

import core.graph._

case class VirusEdge(from: VirusActor, to: VirusActor, weight: Double = 1)
    extends Edge[VirusActor] {
  protected def construct(from: VirusActor, to: VirusActor, weight: Double) =
    VirusEdge(from, to, weight)
}



