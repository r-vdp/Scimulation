package example.social
import core.graph.UndirectedGraph
import core.persistence.GraphRepository
import engine.EventBasedEngine
import engine.Event
import core.graph.Graph
import core.visualize.UbiGraphVisualizer
import engine.SnapshotCreator



object EventBasedSocialSimulation extends App {

  testEngine()

  def testEngine() {
    val graph = new UndirectedGraph[SocialActor, SocialEdge[SocialActor]]

    GraphRepository.loadGraph(graph,"social.xml")

    val engine = new EventBasedEngine(graph, 5)

    engine.addEvent(new Friendship(1,graph,graph.getVertex("2").get,graph.getVertex("0").get))
    engine.addEvent(new Friendship(3,graph,graph.getVertex("4").get,graph.getVertex("0").get))
    engine.addEvent(new Friendship(4,graph,graph.getVertex("0").get,graph.getVertex("5").get))

    val vis = new UbiGraphVisualizer[SocialActor, SocialEdge[SocialActor]]
    vis.subscribeTo(engine)
    
    val snapshots = new SnapshotCreator[SocialActor, SocialEdge[SocialActor]](10,"EvSocout")
    snapshots.subscribeTo(engine)


    engine.run()
        GraphRepository.persistGraph(graph,"EventSocialOut.xml")
  }

}

case class Friendship(t:Int,graph:Graph[SocialActor, SocialEdge[SocialActor]],s1:SocialActor,s2:SocialActor) extends Event[SocialActor](t){
	  override def execute(){
	    println("new friendship between "+s1.getName+" and "+s2.getName)
	    graph.addEdge(new SocialEdge(s1,s2))
	  }
}

