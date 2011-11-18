package example.social
import core.graph.UndirectedGraph
import core.graph.persistence.GraphRepository
import engine.EventBasedEngine
import engine.Event

object EventBasedSocialSimulation extends App {

  testEngine()

  def testEngine() {
    val graph = new UndirectedGraph[SocialActor, SocialEdge[SocialActor]]

    GraphRepository.loadGraph(graph,"social.xml")

    val engine = new EventBasedEngine(graph, 5)

    engine.addEvent(new HealEvent(4,graph.getVertex("second").get))
    engine.addEvent(new InfectEvent(1,graph.getVertex("second").get))
    engine.addEvent(new InfectEvent(3,graph.getVertex("root").get))


    engine.run()
  }



}

case class Row(t:Int,s1:SocialActor,s2:SocialActor) extends Event[SocialActor](t){
	  override def execute(){
	    s1.unfriend(s2)
	  }
}
  case class Friendship(t:Int,s1:SocialActor,s2:SocialActor)extends Event[SocialActor](t){
	  override def execute(){
		s1.befriend(s2)
	  }
}
