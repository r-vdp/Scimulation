package core

/**
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */

object SandBox extends App {
  val graph = new Graph[BaseNode, BaseEdge]

  val root = BaseNode("root")
  val second = BaseNode("second")
  val third = BaseNode("third")
  val fourth = BaseNode("fourth")
  val fifth = BaseNode("fifth")
  val nodes = List(root, second, third, fourth, fifth)

  graph.addNodes(nodes)

  assert(graph.size == nodes.size)

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
  graph.removeNode(second)
  println(graph)

//  printNeighbours(root)
//  printNeighbours(third)
//
//  def printNeighbours(node: Node) {
//    println(node.getNeighbours map (e => e.toString))
//  }
}

class BaseEdge(left: BaseNode, right: BaseNode) extends Edge(left, right)

object BaseEdge {
  def apply(left: BaseNode, right: BaseNode) = new BaseEdge(left, right)
}

case class BaseNode(name: String) extends Node
