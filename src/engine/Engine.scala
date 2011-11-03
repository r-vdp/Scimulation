package engine

import core.graph.{Graph, Edge, Vertex}
import scala.collection.mutable.PriorityQueue

trait Time{
  val time:Integer
  
  def getTime : Integer = time
  
}

trait Event extends Transaction[Vertex] with Time{
  
}

trait Transactions {
  def list: List[Transaction[Vertex]];
  
  def doTransactions() = {
    (list withFilter (_.areAble)) foreach (_.doTransaction)
  }
}

class Transaction[V<:Vertex](f: Seq[V] => Seq[V],stakeholders: List[V with Action[V]]) {
  def areAble[V <: Vertex]: Boolean =
    (true /: stakeholders) (_ && _.isAble)
  
  def doTransaction = {};
}

trait Action[V <: Vertex] {
  def isAble: Boolean

  def execute(): V with Action[V];
}


class Engine[V <: Vertex, E <: Edge[V]](graph: Graph[V, E]) {

  type AV = V with Action[V]
  type TV = V with Transactions
  
  
  var time:Integer = 0
  var eventList:PriorityQueue[Event] = _;
  
  
  def run(f: Seq[V] => Seq[V], count: Int) {
    var vertices = graph.toSeq
    for (time <- 0 until count) {
      vertices = f(vertices)
    }
  }

  
  def roundBased(list: List[TV]): List[TV] = {
		  list foreach (_.doTransactions)
		  list
  }

  def turnBased(list: List[AV]): List[AV] = {
    list.head.execute() :: turnBased(list.tail)
  }

  def eventBased(list: List[V]): List[V] = {
   
    
    while(time == eventList.head.getTime){
         eventList.dequeue().doTransaction
    }
    
    list
  }
  
  def addEvent(event:Event)={
	  eventList.+=(event)
  }
  
}
