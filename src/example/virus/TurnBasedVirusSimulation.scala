package example.virus

import scala.collection.mutable.Map
import core.graph._
import engine.TurnBasedEngine
import core.visualize.UbiGraphVisualizer
import persistence.GraphRepository
import core.statistics.StatisticsManager


object TurnBasedVirusSimulation extends App {

  testEngine()

  def testEngine() {
    val graph = new UndirectedGraph[VirusActor, VirusEdge[VirusActor]]
    
    GraphRepository.loadGraph(graph,"tbsim.xml")

    val engine = new TurnBasedEngine(graph, 20)
    val vis = new UbiGraphVisualizer[VirusActor, VirusEdge[VirusActor]]
    vis.subscribeTo(engine)
    
    val stat = new StatisticsManager[VirusActor, VirusEdge[VirusActor]]
    stat.subscribeTo(engine)
    

    engine.run()
  }
}
