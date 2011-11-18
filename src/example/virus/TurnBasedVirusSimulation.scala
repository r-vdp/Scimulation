package example.virus

import core.graph._
import engine.TurnBasedEngine
import core.visualize.UbiGraphVisualizer
import core.persistence.GraphRepository
import engine.SnapshotCreator

object TurnBasedVirusSimulation extends App {

  testEngine()

  def testEngine() {
    val graph = new UndirectedGraph[VirusActor, VirusEdge[VirusActor]]

    GraphRepository.loadGraph(graph,"tbsim.xml")

    val engine = new TurnBasedEngine(graph, 34)
    val vis = new UbiGraphVisualizer[VirusActor, VirusEdge[VirusActor]]
    vis.subscribeTo(engine)

    val snapshots = new SnapshotCreator[VirusActor, VirusEdge[VirusActor]](10,"TBout")
    snapshots.subscribeTo(engine)

//    val stat = new StatisticsManager[VirusActor, VirusEdge[VirusActor]]
//    stat.subscribeTo(engine)

    engine.run()
    GraphRepository.persistGraph(graph,"TBVirusSimoutput.xml")
  }
}
