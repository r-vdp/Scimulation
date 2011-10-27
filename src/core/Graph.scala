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
      map += (node -> Nil)
    }
  }

  def addEdge(edge: E) {
    if (isLegal(edge)) {
      edge map {
        node => map += (node -> (map(node) += edge))
      }
    }
  }

  def removeEdge(edge: E) {
    if (isLegal(edge)) {
      edge map {
        node => map += (node -> (map(node) filter (node !=)))
      }
    }
  }

  def removeNode(node: N) {

  }

  /**
   * Can this edge be a part of this graph?
   * True iff both the nodes referenced by edge are contained in this graph
   */
  def isLegal(edge: E) = edge forall map.contains
}

trait Node
