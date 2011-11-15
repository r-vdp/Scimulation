package core.graph.visualize

import core.graph.UndirectedGraph
import core.graph.BaseVertex
import core.BaseEdge
import core.BaseEdge
import core.BaseEdge
import core.BaseEdge
import core.BaseEdge
import core.graph.BaseVertex

object VisualizerTest extends App {

  val graph = new UndirectedGraph[BaseVertex, BaseEdge]
  val vis = new UbiGraphVisualizer[BaseVertex, BaseEdge]

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

  vis.visualize(graph)
}
