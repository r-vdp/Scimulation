package example.virus

import scala.collection.mutable.Map
import core.graph._
import engine.Event
import engine.EventBasedEngine
import persistence.GraphRepository

object EventBasedVirusSimulation extends App {

  testEngine()

  def testEngine() {
    val graph = new UndirectedGraph[VirusActor, VirusEdge[VirusActor]]

    GraphRepository.loadGraph(graph,"tbsim.xml")

    val engine = new EventBasedEngine(graph, 5)

    
    
    
    
    engine.addEvent(new HealEvent(4,graph.getVertex("second")))
    engine.addEvent(new InfectEvent(1,graph.getVertex("second")))
    engine.addEvent(new InfectEvent(3,graph.getVertex("root")))


    engine.run()
  }
  

  
}

case class InfectEvent(t:Int,v:VirusActor) extends Event[VirusActor](t,v){
	  override def execute(){
	    v.infect()
	  }
}
  case class HealEvent(t:Int,v:VirusActor) extends Event[VirusActor](t,v){
	  override def execute(){
	    v.heal()
	  }
}
