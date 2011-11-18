package example.virus
import core.graph.Edge
import core.graph.Vertex
import monitor.Subscriber
import core.graph.Graph
import core.persistence.GraphRepository


class SnapshotCreator[V <: Vertex[V], E <: Edge[V]](val interval:Int,val filename:String)
  extends Subscriber[V, E] { 

  var count:Int =0
  
  override def update(graph : Graph[V,E]){
    if(count%interval ==0 && count!=0)
    	GraphRepository.persistGraph(graph,filename+count+".xml")
    count+=1
  }
  
}