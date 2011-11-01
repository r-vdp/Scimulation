package core

import graph._
import graph.mst.{Node, Leaf, Tree}

/**
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */

object SandBox extends App {
  testGraph()
  //testTree()
  testTraversal()

  def testTraversal() {
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

    //println(graph.vertices)
    graph foreach println
    //graph.setTraverser(new EdgeTraverser(graph))
    //graph foreach println
  }

  def testTree() {
    val tree: Tree[Int] =
      Node(
        Leaf(1), Leaf(2), Leaf(3),
        Node(
          Node(
            Leaf(4), Leaf(5), Leaf(6), Node(Leaf(7), Leaf(8)),
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
    val graph = new DirectedGraph[BaseVertex, BaseEdge]

    val root = BaseVertex("root")
    val second = BaseVertex("second")
    val third = BaseVertex("third")
    val fourth = BaseVertex("fourth")
    val fifth = BaseVertex("fifth")
    val vertices = root :: second :: third :: fourth :: fifth ::Nil

    graph.addVertices(vertices)

    assert(graph.size == vertices.size)

    val edges = BaseEdge(root, second) ::
      BaseEdge(second, third) ::
      BaseEdge(root, third) ::
      BaseEdge(second, fourth) ::
      BaseEdge(root, fourth) ::
      BaseEdge(third, fourth) ::
      BaseEdge(root, fifth) :: Nil

    graph.addEdges(edges)
    println(graph + "\n")

    graph.removeEdge(BaseEdge(root, fourth))
    println(graph + "\n")

    graph.removeEdge(BaseEdge(root, fifth))
    assert(!graph.contains(BaseEdge(root, fifth)))
    println(graph + "\n")

    assert(graph.contains(second))
    graph.removeVertex(second)
    println(graph + "\n")

    graph.addVertices(second :: fifth :: Nil)
    graph.addEdges(BaseEdge(second, root) ::
      BaseEdge(second, fourth) ::
      BaseEdge(third, second) ::
      BaseEdge(root, fifth) :: Nil
    )

    println(graph + "\n")
    println("Root neighbours: " + graph.neighbours(root))
    println("Second neighbours: " + graph.neighbours(second))

    graph.neighbours(BaseVertex("none"))
  }
}

class BaseEdge(from: BaseVertex, to: BaseVertex, weight: Double = 1)
    extends Edge(from, to, weight) {
  protected def construct(from: BaseVertex, to: BaseVertex, weight: Double) =
    BaseEdge(from, to, weight)
}

object BaseEdge {
  def apply(from: BaseVertex, to: BaseVertex, weight: Double = 1) =
    new BaseEdge(from, to, weight)
}
