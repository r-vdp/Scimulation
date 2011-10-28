package core

/**
 * Created by Ramses de Norre
 * Date: 27/10/11
 * Time: 10:32
 */
class Graph[N <: Node, E <: Edge[N]] extends Observable {
  private var map: Map[N, Set[E]] = Map.empty

  def addNode(node: N) {
    if (!map.contains(node)) {
      map += (node -> Set.empty)
    }
  }

  def addNodes(nodes: Seq[N]) {
    nodes foreach addNode
  }

  def addEdge(edge: E) {
    if (isLegal(edge)) {
      edge foreach {
        node => map += (node -> (map(node) + edge))
      }
    }
  }

  def addEdges(edges: Seq[E]) {
    edges foreach addEdge
  }

  def removeEdge(edge: E) {
    if (isLegal(edge)) {
      edge foreach {
        node =>
          val rest = map(node) filter (edge !=)
          if (rest.isEmpty) {
            map -= node
          } else {
            map += (node -> rest)
          }
      }
    }
  }

  def removeNode(node: N) {
    if (map.contains(node)) {
      map(node) foreach removeEdge
      assert(map(node).isEmpty)
      map -= node
    }
    throw new IllegalArgumentException
  }

  def size = map.size

  /**
   * Can this edge be a part of this graph?
   * True iff both the nodes referenced by edge are contained in this graph
   */
  def isLegal(edge: E) = edge forall map.contains

  override def toString = {
    for (node <- map.keys;
         edge <- map(node)
    ) yield (edge.left, edge.right).toString()
  } mkString ", "
}

trait Node
