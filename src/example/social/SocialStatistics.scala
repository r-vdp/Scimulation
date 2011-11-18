package example.social

import core.statistics.StatisticsManager
import core.graph.Edge
import core.graph.Vertex

class SocialStatistics[V <: Vertex[V], E <: Edge[V]]
  extends StatisticsManager[V, E] {

  override def chosenUpdates(){
	  if(counter == 10){
	    outPutNrEdges("nrEdgesOn10.csv")
	  }
  }
}
