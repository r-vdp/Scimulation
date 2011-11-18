package example.virus

import engine.RoundBasedEngine
import core.visualize.UbiGraphVisualizer
import core.graph.UndirectedGraph
import core.graph.persistence.GraphRepository

object RoundBasedVirusSimulation extends App {

  testEngine()

  def testEngine() {
    val graph = new UndirectedGraph[RoundVirusActor, VirusEdge[RoundVirusActor]]
    GraphRepository.loadGraph(graph,"rbsim.xml")

    val engine = new RoundBasedEngine(graph, 32)

    val vis = new UbiGraphVisualizer[RoundVirusActor, VirusEdge[RoundVirusActor]]

    vis.subscribeTo(engine)

    engine.run()
  }
}
