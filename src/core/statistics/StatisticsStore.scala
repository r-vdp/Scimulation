package core.statistics


import scala.dbc.result.Tuple
import core.graph.Edge
import core.graph.Vertex
import core.graph.Graph
import scala.collection.mutable.Map

class StatisticsStore [V <: Vertex[V], E <: Edge[V]] {


	var nbofedges = Map[Int, Int]()
    var nbofvertices= Map[Int, Int]()
	var avnbofedgespervertex = Map [Int, Double]()
    var attrbmap = Map[Int, Map[Pair[String,String], List[String]]]()
	var densityofnetwork = Map[Int, Double]()
    var diffattr = Map[Pair[String,String], Map[Int, Int]]()

	

  def densityOfNetwork(t : Int) = {  
	densityofnetwork += t -> (avnbofedgespervertex.get(t) getOrElse(0.0)/(nbofvertices.get(t) getOrElse(2) -1))
    }
	

  def nbOfVerticesWithSameAttribute(t: Int, attr:String, value: String) ={
    
	var pair = (attr, value)
	var localMap = Map[Int,Int]()
	  for (key <- attrbmap.getOrElse(t, Map.empty).keySet){ 
		if ((key._1.equals(pair._1))&&(key._2.equals(pair._2)))
			{
		  localMap += t -> attrbmap.get(t).get(pair).size
		  diffattr += pair -> localMap.++(diffattr.getOrElse(pair, Map[Int,Int]()))
		  }
		}
}
  
  def getStatOfAttribute(attr:String, value:String): Map[Int,Int]={
    val pair = (attr,value)
    diffattr.get(pair) getOrElse(Map.empty)
  }
}