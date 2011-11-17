package core.statistics


import scala.dbc.result.Tuple
import core.graph.Edge
import core.graph.Vertex
import core.graph.Graph
import scala.collection.mutable.Map

class StatisticsStore [V <: Vertex[V], E <: Edge[V]] {

/*
 * Moeilijke structuur, maar heb hem proberen uit te leggen in de mail.   
 * Ik weet niet beter. 
 */
	var nbofedges = Map[Int, Int]()
    var nbofvertices= Map[Int, Int]()
	var avnbofedgespervertex = Map [Int, Double]()
    var attrbmap = Map[Int, Map[Pair[String,Char], List[String]]]()
	var densityofnetwork = Map[Int, Double]()
    var diffattr = Map[Pair[String,Char], Map[Int, Int]]()

	

  def densityOfNetwork(t : Int) = {  
	densityofnetwork += t -> (avnbofedgespervertex.get(t) getOrElse(0.0)/(nbofvertices.get(t) getOrElse(2) -1))
    }
	

  def nbOfVerticesWithSameAttribute(t: Int, attr:String, value: Char) ={
	var pair = (attr, value)
	var localMap = Map[Int,Int]()
	localMap += t -> attrbmap.get(t).get(pair).size
    diffattr += pair -> localMap.++(diffattr.getOrElse(pair, Nil))
}
}