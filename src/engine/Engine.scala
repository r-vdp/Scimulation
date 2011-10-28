package engine
import core.Node

trait Transactions{
  var list : List[Transaction]
  
}

class Transaction(stakeholders :List[Node with Action]){
  def areAble : Boolean = {
    for(n<-stakeholders){
      if(!n.isAble)
        false
    }
    true
  }
}

trait Action {
  def isAble : Boolean
  def execute():Node with Action;
}

class Engine(node:Node){
 
 def run(f:List[Node] => List[Node],count :Int) ={
   var list: List[Node] = node.traverse
   for(i<- 0 until count){
     list = f(list)
   }
 }

 def roundBased(list : List[Node]) : List[Node] = {
   list
 }
 
 def TurnBased(list : List[Node with Action]) : List[Node with Action] = {
   List(list.head.execute) ++ TurnBased(list.tail)
 }
  
 def EventBased(list : List[Node]) : List[Node] = {
   list
 }
 
}