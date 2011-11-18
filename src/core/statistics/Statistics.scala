package core.statistics

import core.graph.Graph
import core.graph.Edge
import core.graph.Vertex
import scala.collection.mutable.Map
import scala.collection.mutable.Set

class Statistics[V <: Vertex[V], E <: Edge[V]] {

  def nbOfEdges(graph: Graph[V, E]): Int = {
    graph.edges.size
  }

  def nbOfVertices(graph: Graph[V, E]): Int = {
    graph.size
  }

  def avNbOfEdgesPerVertex(graph: Graph[V, E]): Double = {
    sum(graph.vertices map (_.neighbours.size)) / graph.size
  }

  private def sum(xs: Seq[Int]) = (0 /: xs)(_ + _)

  /*
  * HashMap van Pair[String,Char], List[String] was het beste wat ik kon verzinnen.
  * maar er is wel een reden voor. De String, Char is zo een beetje de unieke key
  * De List[String] houdt alle id's van de vertices bij.
  * Op die manier kunnen er dingen afgeleid worden aan de hand van een inferencemachine.
  * bijvoorbeeld attr gewicht = 200 kg vertex 5, 3, 7, 10
  * 				attr veteten = true   vertex 7, 3, 6, 8
  * conclusie vertex 3 en 7 kunnen samen naar de gym.
  * conclusie vertex 3 en 7 kunnen een (dikke) cluster vormen?
  *
  */
  def createAttrMap(graph: Graph[V, E]): Map[Pair[String, String], List[String]] = {
    val localMap = Map[Pair[String, String], List[String]]()
    graph foreach addAttributesToMap(localMap)
    localMap
  }

  private def addAttributesToMap
  (localMap: Map[Pair[String, String], List[String]])(v: V)
  : Map[Pair[String, String], List[String]] = {

    val set = Set.empty[Pair[String, String]]
    for (key <- v.params.keySet) {
      set.add(new Pair(key, v.params(key).toString))
    }

    for (pair <- set) {
      localMap += (pair -> (v.id :: (localMap.get(pair) getOrElse List(v.id))))
    }
    localMap
  }
}
