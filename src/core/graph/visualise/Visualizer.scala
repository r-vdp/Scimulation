package core.graph.visualise

import java.util.HashMap

import org.ubiety.ubigraph.UbigraphClient

import core.graph.Edge
import core.graph.Graph
import core.graph.Vertex

class Visualiser{

  val toGraph : UbigraphClient = new UbigraphClient
  
  
   def visualiseGraph[V <: Vertex[V], E <: Edge[V]](graph: Graph[V, E]) {
       toGraph.clear()
       toGraph.setVertexStyleAttribute(0, "shape", "sphere")
       toGraph.setVertexStyleAttribute(0, "size", "0.5")
       toGraph.setEdgeStyleAttribute(0, "arrow", "true" )

       val verticesmap = new HashMap[String, Int]
       for (vertex <- graph.vertices) 
         yield {verticesmap.put(vertex.id, toGraph.newVertex())   
         toGraph.setVertexAttribute(verticesmap.get(vertex.id), "label" , vertex.id)
       }
       for (edge <- graph.edges) 
         yield toGraph.newEdge(verticesmap.get(edge.from.id), verticesmap.get(edge.to.id))
         
   }
}




