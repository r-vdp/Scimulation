package core

import graph.mst.{Node, Leaf, Tree}
import graph.{Edge, Vertex, Graph}

/**
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */

object SandBox extends App {
  testGraph()
  testTree()

  def testTree() {
    val tree: Tree[Int] =
      Node(
        Leaf(2),
        Node(
          Node(
            Leaf(4),
            Node(
              Leaf(3),
              Leaf(2)
            )
          ),
          Leaf(50)
        )
      )
    tree foreach println
  }

  def testGraph() {
    val graph = new Graph[BaseVertex, BaseEdge]

    val root = BaseVertex("root")
    val second = BaseVertex("second")
    val third = BaseVertex("third")
    val fourth = BaseVertex("fourth")
    val fifth = BaseVertex("fifth")
    val vertices = List(root, second, third, fourth, fifth)

    graph.addVertices(vertices)

    assert(graph.size == vertices.size)

    val edges = List(
      BaseEdge(root, second),
      BaseEdge(second, third),
      BaseEdge(root, third),
      BaseEdge(second, fourth),
      BaseEdge(root, fourth),
      BaseEdge(third, fourth),
      BaseEdge(root, fifth)
    )
    graph.addEdges(edges)
    println(graph + "\n")

    graph.removeEdge(BaseEdge(root, fourth))
    println(graph + "\n")

    graph.removeEdge(BaseEdge(root, fifth))
    assert(!graph.contains(fifth))
    println(graph + "\n")

    assert(graph.contains(second))
    graph.removeVertex(second)
    println(graph + "\n")

    graph.addVertices(List(second, fifth))
    graph.addEdges(List(
      BaseEdge(second, root),
      BaseEdge(second, fourth),
      BaseEdge(third, second),
      BaseEdge(root, fifth)
    ))

    println(graph + "\n")
    println("Root neighbours: " + graph.getNeighbours(root))
    println("Second neighbours: " + graph.getNeighbours(second))

    graph.getNeighbours(BaseVertex("none"))
  }
}

class BaseEdge(left: BaseVertex, right: BaseVertex) extends Edge(left, right) {
  val weight = 1d
}

object BaseEdge {
  def apply(left: BaseVertex, right: BaseVertex) = new BaseEdge(left, right)
}

case class BaseVertex(name: String) extends Vertex
