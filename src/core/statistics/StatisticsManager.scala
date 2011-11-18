package core.statistics


import core.graph.Graph
import core.graph.Edge
import core.graph.Vertex
import scala.collection.mutable.HashMap
import monitor.Subscriber
import core.statistics.CSVOutputStatistics

/*
 * De bedoeling is dus dat iedere simulatie hier een implementatie van maakt met zijn eigen update 
 * Mss moeten die definities die hieronder staan helemaal niet hier staan? Of zijn die mss ook gewoon abstract?
 */

abstract class StatisticsManager[V <: Vertex[V], E <: Edge[V]]extends Subscriber[V, E] {
  
	var counter :Int = 0
  
	val stat = new Statistics[V,E]

    val store = new StatisticsStore[V,E]

	
override
  def update(graph: Graph[V, E]){
	  storeInfoOnNetwork(graph, counter)
	  chosenUpdates()
	  counter+=1
	}
	
 def chosenUpdates()
    

  def storeInfoOnNetwork(graph:Graph[V, E],t: Int){
    val graph1 = graph.deepCopy
    store.nbofedges += t -> stat.nbOfEdges(graph1)
    store.nbofvertices += t -> stat.nbOfEdges(graph1)
    store.avnbofedgespervertex += t -> stat.avNbOfEdgesPerVertex(graph1)
    store.attrbmap += t -> stat.createAttrMap(graph1)
  }
  	
  
    //Voor deze twee onderstaande functies wordt geen gebruik gemaakt 
  	//van de graaf maar van de reeds opgeslagen gegevens
  def storeDensityOfNetwork(t:Int){
    store.densityOfNetwork(t)
  }
  
  
  // Bij implementatie voor 1 simulatie invullen attr en value, naar gelang dewelke opgeslagen moeten worden
  def storeNbOfVerticesWithSameAttribute(t: Int, attr:String, value: String){
    store.nbOfVerticesWithSameAttribute(t, attr, value)
  }
  
   def outPut(attr:String, value:String, filename: String ){
    CSVOutputStatistics.generateCsvFile(store.getStatOfAttribute(attr,value), filename)
  }
  
   def outPutNrEdges(filename:String){
     CSVOutputStatistics.generateCsvFile(store.nbofedges,filename)
   }


  
}