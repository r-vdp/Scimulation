package core.graph.statistics

import java.util.HashMap
import scala.dbc.result.Tuple
import core.graph.Edge
import core.graph.Vertex
import core.graph.Graph

class StatisticsStore [V <: Vertex[V], E <: Edge[V]] {
    //Moet hier iets Lazy?
	val nbofedges = new HashMap[Int, Int]
    val nbofvertices = new HashMap[Int, Int]
	val avnbofedgespervertex = new HashMap [Int, Double]
    val attrbmap = new HashMap[Int, HashMap[Pair[String,Char], List[String]]]
	lazy val densityofnetwork = new HashMap[Int, Double]
    val diffattr = new HashMap[Pair[String,Char], HashMap[Int, Int]]
	
	
  def densityOfNetwork(t : Int) = {
	densityofnetwork.put(t,avnbofedgespervertex.get(t)/(nbofvertices.get(t)-1))
    }
  //wat een lelijke code, Lynn !
  def nbOfVerticesWithSameAttribute(t: Int, attr:String, value: Char) ={
	val pair = (attr, value)
    if (!diffattr.containsKey(pair))  diffattr.put(pair, new HashMap(t, attrbmap.get(t).get(pair).size))
    else diffattr.get(pair).put(t,attrbmap.get(t).get(pair).size)
  }
 
  
  

}