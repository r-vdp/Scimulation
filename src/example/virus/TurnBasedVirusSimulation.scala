package example.virus

import scala.collection.mutable.Map
import core.graph._
import engine.TurnBasedEngine
import core.visualize.UbiGraphVisualizer

object TurnBasedVirusSimulation extends App {

  testEngine()

  def testEngine() {
    val graph = new UndirectedGraph[VirusActor, VirusEdge[VirusActor]]

    var rootMap: Map[String, Any] = Map.empty
    rootMap += "status" -> Status.I
    rootMap += "probability" -> 3
    rootMap += "gender" -> Gender.Male
    rootMap += "progSkill" -> 0
    val root = VirusActor("1", rootMap)

    var secondMap: Map[String, Any] = Map.empty
    secondMap += "status" -> Status.S
    secondMap += "probability" -> 6
    secondMap += "gender" -> Gender.Female
    secondMap += "progSkill" -> 0
    val second = VirusActor("2", secondMap)

    var thirdMap: Map[String, Any] = Map.empty
    thirdMap += "status" -> Status.NI
    thirdMap += "probability" -> 2
    thirdMap += "gender" -> Gender.Male
    thirdMap += "progSkill" -> 0
    val third = VirusActor("3", thirdMap)

    var fourthMap: Map[String, Any] = Map.empty
    fourthMap += "status" -> Status.S
    fourthMap += "probability" -> 7
    fourthMap += "gender" -> Gender.Female
    fourthMap += "progSkill" -> 0
    val fourth = VirusActor("4", fourthMap)

    var fifthMap: Map[String, Any] = Map.empty
    fifthMap += "status" -> Status.S
    fifthMap += "probability" -> 5
    fifthMap += "gender" -> Gender.Male
    fifthMap += "progSkill" -> 0
    val fifth = VirusActor("5", fifthMap)

    var sixMap: Map[String, Any] = Map.empty
    sixMap += "status" -> Status.S
    sixMap += "probability" -> 5
    sixMap += "gender" -> Gender.Male
    sixMap += "progSkill" -> 0
    val six = VirusActor("6", sixMap)

    var sevenMap: Map[String, Any] = Map.empty
    sevenMap += "status" -> Status.S
    sevenMap += "probability" -> 5
    sevenMap += "gender" -> Gender.Male
    sevenMap += "progSkill" -> 0
    val seven = VirusActor("7", sevenMap)

    var eightMap: Map[String, Any] = Map.empty
    eightMap += "status" -> Status.S
    eightMap += "probability" -> 5
    eightMap += "gender" -> Gender.Male
    eightMap += "progSkill" -> 0
    val eight = VirusActor("8", eightMap)

    var daveMap: Map[String, Any] = Map.empty
    daveMap += "status" -> Status.S
    daveMap += "probability" -> 5
    daveMap += "gender" -> Gender.Male
    daveMap += "name" -> "Dave"
    val dave = VirusActor("Dave", daveMap)

    val vertices = root :: second :: third :: fourth :: fifth :: six :: seven :: eight :: dave :: Nil

    graph.addVertices(vertices)

    val edges = VirusEdge(root, second) ::
                VirusEdge(second, third) ::
                VirusEdge(root, dave) ::
                VirusEdge(second, fourth) ::
                VirusEdge(root, fourth) ::
                VirusEdge(third, fourth) ::
                VirusEdge(dave, fifth) ::
                VirusEdge(fifth, six) ::
                VirusEdge(dave, seven) ::
                VirusEdge(dave, eight) :: Nil

    graph.addEdges(edges)

    val engine = new TurnBasedEngine(graph, 20)
    val vis = new UbiGraphVisualizer[VirusActor, VirusEdge[VirusActor]]

    vis.subscribeTo(engine)

    engine.run()
  }
}
