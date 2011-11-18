package engine

import core.graph.{Graph, Edge, Vertex}
import scala.collection.mutable.PriorityQueue
import scala.collection.mutable.ArrayBuffer
import monitor.Publisher

abstract class Event[V <: Vertex[V]](t:Int,node:V)
  extends Action[V] with Ordered[Event[V]]{
	val time = t
	def compare(t:Event[V])= if (t.time < this.time) -1 else if (this.time == t.time) 0 else 1
}

trait Multiverse[V <: Vertex[V]] extends Ordered[V] {

  def calcPrior():Int
  def compare(r:Any with Multiverse[V]) =
    r.calcPrior() compare this.calcPrior()
}

trait Action[V <: Vertex[V]] {
  def execute();
}

abstract class Engine{
  def run()
}

class TurnBasedEngine[V <: Vertex[V] with Action[V], E <: Edge[V]]
    (graph: Graph[V, E],count:Int) extends Engine with Publisher[V,E]{
  override def run() {
        publish(graph)
        Thread.sleep(1000,1000)
        for (time <- 0 until count){
          println("The time is: "+time)
          graph.foreach(_.execute())
          publish(graph)
          Thread.sleep(1000,1000)
        }
  }
  override def publish(g:Graph[V,E]){
    list.foreach{e=>e.update(g)}
  }
}

class RoundBasedEngine
  [V <: Vertex[V] with Multiverse[V] with Action[V], E <: Edge[V]]
    (graph: Graph[V, E],count:Int) extends Engine with Publisher[V,E]{



	override def run() {

	  val g3 = graph.deepCopy
        publish(graph)
        Thread.sleep(1000,1000)
	  for (time <- 0 until count){
		  println("The time is: "+time)
	      var list = new ArrayBuffer[PriorityQueue[V]]
		  for(_<-0 until graph.size)
			  list += PriorityQueue.empty[V]

		  for(i <-0 until graph.size){
			  val g2 = g3.deepCopy
		      g2.take(i+1).last.execute()
		      for(j<-0 until graph.size){
		        list.take(j+1).last += g2.take(j+1).last
		      }
	  }

		  var vertices = new ArrayBuffer[V]
			   for(g <- list)
			     vertices += g.head
	      publish(graph)
          Thread.sleep(1000,1000)
	  }

	}
	  override def publish(g:Graph[V,E]){
    list.foreach{e=>e.update(g)}
  }
}

class EventBasedEngine[V <: Vertex[V], E <: Edge[V]]
    (graph: Graph[V, E],count:Int) extends Engine {

  var eventList = PriorityQueue.empty[Event[V]]

  override def run() {
    for (time <- 0 until count) {
      println("The time is: "+time)
      while(!eventList.isEmpty && time == eventList.head.time) {
        eventList.dequeue().execute()
        }
    }
  }

  def addEvent(event: Event[V]) {
	  eventList += event
  }
}
