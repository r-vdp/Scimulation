package example.virus

import core.graph._
import engine.Event
import engine.EventBasedEngine
import core.persistence.GraphRepository

object EventBasedVirusSimulation extends App {

  testEngine()

  def testEngine() {
    val graph = new UndirectedGraph[VirusActor, VirusEdge[VirusActor]]

    GraphRepository.loadGraph(graph,"tbsim.xml")

    val engine = new EventBasedEngine(graph, 5)

    engine.addEvent(new HealEvent(4,graph.getVertex("second").get))
    engine.addEvent(new InfectEvent(1,graph.getVertex("second").get))
    engine.addEvent(new InfectEvent(3,graph.getVertex("root").get))


    engine.run()
  }



}

case class InfectEvent(t:Int,v:VirusActor) extends Event[VirusActor](t){
	  override def execute(){
	    v.infectious
	  }
}
  case class HealEvent(t:Int,v:VirusActor) extends Event[VirusActor](t){
	  override def execute(){
	    v.heal
	  }
}
