package core

/**
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */

object SandBox extends App {
  val root = BaseNode("root")
  root.addEdge(BaseEdge(root, BaseNode("second")))
  val third = BaseNode("third")
  root.addEdge(BaseEdge(third, root))
  third.addEdge(BaseEdge(third, BaseNode("fourth")))

  printNeighbours(root)
  printNeighbours(third)

  def printNeighbours(node: Node) {
    println(node.getNeighbours map (e => e.toString))
  }
}

trait Node {
  private var edges: List[Edge] = Nil;

  def addEdge(edge: Edge) {
    if (canAdd(edge)) {
      edges = edge :: edges
      edge.other(this).addEdge(edge)
    }
  }

  def canAdd(edge: Edge): Boolean = {
    edge.contains(this) && ! edges.contains(edge)
  }

  def getNeighbours: List[Node] =
    edges map (edge => edge.other(this))
}

abstract case class Edge(left: Node, right: Node) {
  def contains(node: Node) = left == node || right == node
  def other(node: Node) = if (left == node) right else left
}

class BaseEdge(left: Node, right: Node) extends Edge(left, right)

object BaseEdge {
  def apply(left: Node, right: Node) = new BaseEdge(left, right)
}

class BaseNode(val name: String) extends Node {
  override def toString = name
}

object BaseNode {
  def apply(name: String): BaseNode = new BaseNode(name)
}
