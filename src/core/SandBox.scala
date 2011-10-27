package core

/**
 * Created by Ramses de Norre
 * Date: 24/10/11
 * Time: 16:41
 */

object SandBox extends App {
//  val root = BaseNode("root")
//  root.addEdge(BaseEdge(root, BaseNode("second")))
//  val third = BaseNode("third")
//  root.addEdge(BaseEdge(third, root))
//  third.addEdge(BaseEdge(third, BaseNode("fourth")))
//
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

class BaseNode(val name: String) extends Node {
  override def toString = name
}

object BaseNode {
  def apply(name: String): BaseNode = new BaseNode(name)
}
