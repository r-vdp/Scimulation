package example.virus

import engine.RoundBasedEngine
import core.visualize.UbiGraphVisualizer
import core.graph.UndirectedGraph
import core.persistence.GraphRepository
import engine.SnapshotCreator 

object RoundBasedVirusSimulation extends App {

  testEngine()

  def testEngine() {
    val graph = new UndirectedGraph[RoundVirusActor, VirusEdge[RoundVirusActor]]
    GraphRepository.loadGraph(graph,"rbsim.xml")

    val engine = new RoundBasedEngine(graph, 34)

    val vis = new UbiGraphVisualizer[RoundVirusActor, VirusEdge[RoundVirusActor]]
    vis.subscribeTo(engine)

    val snapshots = new SnapshotCreator[RoundVirusActor, VirusEdge[RoundVirusActor]](10,"RBout")
    snapshots.subscribeTo(engine)

//    val stat = new StatisticsManager[RoundVirusActor, VirusEdge[RoundVirusActor]]
//    stat.subscribeTo(engine)


    engine.run()
    GraphRepository.persistGraph(graph,"RBVirusSimoutput.xml")
  }
}
