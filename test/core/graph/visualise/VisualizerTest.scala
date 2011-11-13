package core.graph.visualise

import org.scalatest.{BeforeAndAfterEach, Tag, FunSuite}
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import core.graph.Graph
import core.graph.DirectedGraph
import core.graph.UndirectedGraph
import core.graph.BaseVertex
import core.BaseEdge
/*
 * 
 */

@RunWith(classOf[JUnitRunner])
class VisualizerTest extends FunSuite{
  
	val vis = new Visualiser
	
	val graph = new UndirectedGraph[BaseVertex, BaseEdge]

    val root = BaseVertex("root")
    val second = BaseVertex("second")
    val third = BaseVertex("third")
    val fourth = BaseVertex("fourth")
    val fifth = BaseVertex("fifth")
    val vertices = root :: second :: third :: fourth :: fifth :: Nil

    graph.addVertices(vertices)
    graph.addVertex(second)

    val edges = BaseEdge(root, second) ::
                BaseEdge(second, third) ::
                BaseEdge(root, third) ::
                BaseEdge(second, fourth, 5) ::
                BaseEdge(root, fourth) ::
                BaseEdge(third, fourth) ::
                BaseEdge(root, fifth) :: Nil

    graph.addEdges(edges)
   
    vis.visualiseGraph(graph)
	


}