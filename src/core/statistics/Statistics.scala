package core.statistics

import core.graph.Graph
import core.graph.Edge
import core.graph.Vertex
import java.util.HashMap

class Statistics[V <: Vertex[V], E <: Edge[V]] {

  def nbOfEdges(graph: Graph[V, E]): Int = {
    graph.edges.size
  }

  def nbOfVertices(graph: Graph[V, E]): Int = {
    graph.vertices.size
  }

  def avNbOfEdgesPerVertex(graph: Graph[V, E]): Double = {
    (sum
       (for (vertex <- graph.vertices)
       yield {
         vertex.neighbours.size
       })
     / nbOfVertices(graph))
  }

  private def sum(xs: Set[Int]) = {
    (0 /: xs)((x, y) => x + y)
  }

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

  def createAttrMap(graph: Graph[V, E]): HashMap[Pair[String, Char], List[String]] = {
    val localMap = new HashMap[Pair[String, Char], List[String]]
    graph foreach addAttributesToMap(localMap)
    localMap
  }

  // Fugly!!
  private def addAttributesToMap(localMap: HashMap[Pair[String, Char], List[String]])
                                (v: V):
  HashMap[Pair[String, Char], List[String]] = {
    val zip = v.params.keySet.zip(v.params.values.toString())
    for (pair <- zip) {
      if (!localMap.keySet.contains(pair)) {
        localMap.put(pair, List(v.id))
      }
      else {
        localMap.put(pair, v.id :: localMap.get(pair))
      }
    }
    localMap
  }

}
