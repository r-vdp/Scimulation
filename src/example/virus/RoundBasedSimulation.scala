package example.virus

import scala.collection.mutable.Map
import scala.collection.mutable.Set
import core.graph._
import engine.RoundBasedEngine
import core.visualize.UbiGraphVisualizer
import core.graph.UndirectedGraph
import core.graph.persistence.GraphRepository

object RoundBasedVirusSimulation extends App {

  testEngine()

  def testEngine() {
    val graph = new UndirectedGraph[RoundVirusActor, VirusEdge[RoundVirusActor]]
    GraphRepository.loadGraph(graph,"rbsim.xml")
    
    val engine = new RoundBasedEngine(graph, 5)
    
    val vis = new UbiGraphVisualizer[RoundVirusActor, VirusEdge[RoundVirusActor]]

    vis.subscribeTo(engine)    

    engine.run()
  }
}
