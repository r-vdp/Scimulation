package core.visualize

import scala.collection.mutable.Map
import core.graph.Edge
import core.graph.Graph
import core.graph.Vertex
import org.ubiety.ubigraph.UbigraphClient
import monitor.Subscriber

class UbiGraphVisualizer[V <: Vertex[V] with Color, E <: Edge[V]]
  extends Subscriber[V, E] {

	private[this] var check = false
	private[this] val ubiClient: UbigraphClient = init()
	private[this] val verticesmap = Map.empty[String, Int]
	private[this] val edgesmap = Map.empty[E, Int]
	private var edgesSet = Set[E]()

  override def update(graph: Graph[V, E]) {
    visualize(graph);
  }

  def visualize(graph: Graph[V, E]) {
    if(!check) {
	    graph foreach addVertex(ubiClient, verticesmap)
	    graph.edges foreach addEdge(ubiClient, verticesmap)
	    check = true
    } else {
      graph.edges foreach addEdge(ubiClient, verticesmap)
      graph foreach updateVertex(ubiClient, verticesmap)
      val temp =(edgesSet -- graph.edges)
      temp foreach removeEdge(ubiClient, edgesmap)
      edgesSet = edgesSet -- temp
    }
  }

  private[this] def init(): UbigraphClient = {
    val ubiClient = new UbigraphClient()
    ubiClient.clear()
    ubiClient.setVertexStyleAttribute(0, "shape", "sphere")
    ubiClient.setVertexStyleAttribute(0, "size", "0.5")
    ubiClient.setEdgeStyleAttribute(0, "arrow", "true")
    ubiClient
  }

  private[this] def addVertex
  (ubiGraph: UbigraphClient, map: Map[String, Int])(v: V) {
    map.put(v.id, ubiGraph.newVertex())
    ubiGraph.setVertexAttribute(map(v.id), "label", v.id)
    ubiGraph.setVertexAttribute(map(v.id), "color", v.color)
  }

  private [this] def updateVertex
  (ubiGraph: UbigraphClient, map: Map[String, Int])(v: V){
    ubiGraph.setVertexAttribute(map(v.id), "color", v.color)
  }

  private[this] def addEdge
  (ubiGraph: UbigraphClient, map: Map[String, Int])(e: E) {
    if(!edgesSet.contains(e)){
    	edgesmap +=(e)->ubiGraph.newEdge(map(e.from.id), map(e.to.id))
    	edgesSet +=e
    }
  }
    private[this] def removeEdge
  (ubiGraph: UbigraphClient, map: Map[E, Int])(e: E) {
    	ubiGraph.removeEdge(map(e))
  }
}
