package engine

import core.graph.{Graph, Edge, Vertex}


trait Transactions {
  def list: List[Transaction];
}

class Transaction {
  def areAble[V <: Vertex](stakeholders: List[V with Action[V]]): Boolean =
    (true /: stakeholders) (_ && _.isAble)
}

trait Action[V <: Vertex] {
  def isAble: Boolean

  def execute(): V with Action[V];
}

class Engine[V <: Vertex, E <: Edge[V]](graph: Graph[V, E]) {

  type AV = V with Action[V]

  def run(f: Seq[V] => Seq[V], count: Int) {
    var vertices = graph.toSeq
    for (_ <- 0 until count) {
      vertices = f(vertices)
    }
  }

  def roundBased(list: List[V]): List[V] = {
    list
  }

  def TurnBased(list: List[AV]): List[AV] = {
    list.head.execute() :: TurnBased(list.tail)
  }

  def EventBased(list: List[V]): List[V] = {
    list
  }
}
