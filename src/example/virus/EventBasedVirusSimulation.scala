package example.virus

import scala.collection.mutable.Map
import core.graph._
import engine.TurnBasedEngine
import engine.Event
import engine.EventBasedEngine

object EventBasedVirusSimulation extends App {

  testEngine()

  def testEngine() {
    val graph = new UndirectedGraph[VirusActor, VirusEdge]

    var rootMap: Map[String, Any] = Map.empty
    rootMap += "status" -> Status.S
    rootMap += "probability" -> 3
    rootMap += "gender" -> Gender.Male
    val root = VirusActor("root", rootMap)

    var secondMap: Map[String, Any] = Map.empty
    secondMap += "status" -> Status.S
    secondMap += "probability" -> 6
    secondMap += "gender" -> Gender.Female
    val second = VirusActor("second", secondMap)

    var thirdMap: Map[String, Any] = Map.empty
    thirdMap += "status" -> Status.NI
    thirdMap += "probability" -> 2
    thirdMap += "gender" -> Gender.Male
    val third = VirusActor("third", thirdMap)

    var fourthMap: Map[String, Any] = Map.empty
    fourthMap += "status" -> Status.S
    fourthMap += "probability" -> 7
    fourthMap += "gender" -> Gender.Female
    val fourth = VirusActor("fourth", fourthMap)

    var fifthMap: Map[String, Any] = Map.empty
    fifthMap += "status" -> Status.S
    fifthMap += "probability" -> 5
    rootMap += "gender" -> Gender.Male
    val fifth = VirusActor("fifth", fifthMap)

    val vertices = root :: second :: third :: fourth :: fifth :: Nil

    graph.addVertices(vertices)

    val edges = VirusEdge(root, second) ::
                VirusEdge(second, third) ::
                VirusEdge(root, third) ::
                VirusEdge(second, fourth) ::
                VirusEdge(root, fourth) ::
                VirusEdge(third, fourth) ::
                VirusEdge(root, fifth) :: Nil

    graph.addEdges(edges)
    //println(graph + "\n")

    val engine = new EventBasedEngine(graph, 5)
    
    engine.addEvent(new HealEvent(4,second))
    engine.addEvent(new InfectEvent(1,second)) 
    engine.addEvent(new InfectEvent(3,root))
    

    engine.run()
  }
}

case class InfectEvent(t:Int,v:VirusActor) extends Event[VirusActor](t,v){
	  override def isAble = true
	  override def execute(){
	    v.infect()
	  }
}
  case class HealEvent(t:Int,v:VirusActor) extends Event[VirusActor](t,v){
	  override def isAble = true
	  override def execute(){
	    v.heal()
	  }
}
