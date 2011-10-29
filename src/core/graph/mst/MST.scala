package core.graph.mst

import core.graph.{Edge, Vertex, Graph}
import scala.collection.mutable.PriorityQueue

/**
 * Created by Ramses de Norre
 * Date: 28/10/11
 * Time: 16:34
 */
private class MST[V <: Vertex, E <: Edge[V]] {

  /**
   * Make the priorityqueue sort in descending order by reversing the
   * argument order of compare here.
   * PriorityQueue sorts in ascending order wrt the compare function.
   */
  implicit def edgeToOrdered(edge: E) = new Ordered[E] {
    def compare(that: E) = that compare edge
  }

  var mst: Map[V, E] = Map.empty
  var inTree: Set[V] = Set.empty
  val pq: PriorityQueue[E] = PriorityQueue.empty[E]

  def apply(graph: Graph[V, E]): Iterable[V] = {
    mkTree(findMST(graph))
  }

  def findMST(graph: Graph[V, E]): Map[V, E] = {
    visit(graph, graph.someVertex)
    while (!pq.isEmpty) {
      val e = pq.dequeue()
      assert((e filter (!inTree.contains(_))).size < 2)

      e filter (!inTree.contains(_)) foreach { v =>
        mst += (v -> e)
        visit(graph, v)
      }
    }
    mst
  }

  def visit(graph: Graph[V, E], v: V) {
    inTree += v
    graph.adjacentVertices(v) filter { e =>
      !inTree.contains(e.other(v).get)
    } foreach (pq += _)
  }

  def mkTree(mst: Map[V, E]): Iterable[V] = {
    //todo?
    Empty
  }
}

object MST {
  def apply[V <: Vertex, E <: Edge[V]] (graph: Graph[V, E]): Iterable[V] =
    (new MST[V, E])(graph)
}
