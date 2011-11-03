package engine

import core.graph.{Graph, Edge, Vertex}
import java.util.PriorityQueue

trait Time{
  val time:Integer
  
  def getTime : Integer = time
  
}

trait Event extends Transaction with Time{
  
}

trait Transactions[V <: Vertex] {
  def list: List[Transaction];
  
  def doTransactions() = {
    for (t <-list) {
      if(t.areAble){
        t.doTransaction
      }
    }
  }
}

class Transaction(f: Seq[Vertex] => Seq[Vertex],stakeholders: List[Vertex with Action[Vertex]]) {
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
  type TV = V with Transactions[V]
  
  
  var time:Integer = 0
  var eventList:PriorityQueue[Event] = _;
  
  
  def run(f: Seq[V] => Seq[V], count: Int) {
    var vertices = graph.toSeq
    for (time <- 0 until count) {
      vertices = f(vertices)
    }
  }

  // list nodes => node heeft queue transac, gaan die af,
  
  def roundBased(list: List[TV]): List[TV] = {
     for(t<-list){
       t.doTransactions
     }
     list
  }

  def turnBased(list: List[AV]): List[AV] = {
    list.head.execute() :: turnBased(list.tail)
  }

  def eventBased(list: List[V]): List[V] = {
   
    
    while(time == eventList.peek().getTime){
      eventList.poll.doTransaction
    }
    
    list
  }
  
  def addEvent(event:Event)={
	  eventList.add(event)
  }
  
}
