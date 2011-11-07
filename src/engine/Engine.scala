package engine

import core.graph.{Graph, Edge, Vertex}
import scala.collection.mutable.PriorityQueue

abstract class Event[V<:Vertex](t:Int,node:V) extends Action[V]  with Ordered[Event[V]]{
	val time = t
	def compare(t:Event[V])= if (t.time < this.time) -1 else if (this.time == t.time) 0 else 1
}

trait Transactions {
  def list: List[Transaction[Vertex]];

  def doTransactions() {
    (list withFilter (_.areAble)) foreach (_.doTransaction())
  }
}

class Transaction[V<:Vertex](f: Seq[V] => Seq[V],stakeholders: List[V with Action[V]]) {
  def areAble[V <: Vertex]: Boolean =
    (true /: stakeholders) (_ && _.isAble)

  def doTransaction() {}
}

trait Action[V <: Vertex]{
  def isAble: Boolean

  def execute();

}

abstract class Engine{
  def run()
}

class TurnBasedEngine[V <: Vertex with Action[V], E <: Edge[V]]
    (graph: Graph[V, E],count:Int) extends Engine{
  override def run() {
        for (time <- 0 until count){
          println("The time is: "+time)
          graph.foreach(_.execute())
        }
  }
}

class RoundBasedEngine[V <: Vertex with Transactions, E <: Edge[V]]
    (graph: Graph[V, E],count:Int) extends Engine{
	override def run() {
	  for (_ <- 0 until count)
		  graph.foreach(_.doTransactions())
  }
}

class EventBasedEngine[V <: Vertex, E <: Edge[V]]
    (graph: Graph[V, E],count:Int) extends Engine{

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

//class Engine[V <: Vertex, E <: Edge[V]](graph: Graph[V, E]) {
//
//  type AV = V with Action[V]
//  type TV = V with Transactions
//
//
//  var time:Int = 0
//  var eventList = PriorityQueue.empty[Event]
//
//
//  def run(f: Seq[V] => Seq[V], count: Int) {
//    var vertices = graph.toSeq
//    for (time <- 0 until count) {
//      vertices = f(vertices)
//    }
//  }
//
//
//  def roundBased(list: Seq[TV]): Seq[TV] = {
//		  list foreach (_.doTransactions)
//		  list
//  }
//
//  def turnBased(list: Seq[V with Action[V]]): Seq[V with Action[V]] = {
//		  list foreach (_.execute())
//		  list
//  }
//
//  def eventBased(list: Seq[V]): Seq[V] = {
//
//
//    while(time == eventList.head.time){
//         eventList.dequeue().doTransaction
//    }
//
//    list
//  }
//



