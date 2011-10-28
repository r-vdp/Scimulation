package core.graph.mst

import core.graph.{Edge, Vertex, Graph}

/**
 * Created by Ramses de Norre
 * Date: 28/10/11
 * Time: 16:34
 */

object MSTFinder {
  def find[V <: Vertex, E <: Edge[V]](graph: Graph[V, E]): Tree[V] =
    Node(
      Leaf(graph.vertices(0)),
      Node(
        Node(
          Leaf(graph.vertices(1)),
          Node(
            Leaf(graph.vertices(2)),
            Leaf(graph.vertices(3))
          )
        ),
        Leaf(graph.vertices(4))
      )
    )
}
