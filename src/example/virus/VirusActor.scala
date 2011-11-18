package example.virus

import scala.collection.mutable.Map
import engine.Action
import core.graph.Vertex
import core.visualize.Color

object Status extends Enumeration {
  
  
  type Status = Value
 
  val NI = new Value {
    override def id = 1
    override def toString = "NI"
  }
  
  val S = new Value{
    override def id = 2
    override def toString = "S"
  }
  val I = new Value{
    override def id = 3
    override def toString = "I"
  }
  val R = new Value{
    override def id = 4
    override def toString = "R"
  }
}

object Gender extends Enumeration {
  type Gender = Value
    val Male = new Value{
    override def id = 1
    override def toString = "Male"
  }
    val Female = new Value{
    override def id = 2
    override def toString = "Female"
  }
}

import example.virus.Status._

// zouden graag with action[] eruit trekken zodat de TurnbasedVirusSimulation
// gebruiker een nieuw kind maakt die extend virusactor with Action
// idem voor roundVirusactor, die dan grotendeels zou wegsmelten
// hadden errors met overerving
class VirusActor(inId: String, inMap: Map[String, Any])
  extends Vertex[VirusActor] with Action[VirusActor] with Color {

  override lazy val id = inId
  override lazy val params = inMap

  override def color: String = {
    if (getStatus == Status.I.toString()) {
      "#ff0000"
    } else if (getName == "Dave") {
      "#ff00ff"
    } else {
      println((255 - getProgSkill % 255))
      "#" + (255 - getProgSkill % 255).toHexString + "ff" + (255 - getProgSkill % 255).toHexString
    } 
  }

  object AllDone extends Exception { }

  override def execute() {
    if (getStatus == Status.S && getName != "Dave") {
      try{
	      neighbours.foreach{e=>
	        if(e.getStatus==Status.I){
	          infect
	          throw AllDone
	        } else if(e.getName=="Dave"){
	          incProgSkill
	        }
	      }
      } catch {
		  case AllDone =>
	  }
      
    } else if (getStatus == Status.I.toString() && getGender == Gender.Female.toString()) {
      heal()
    }
  }

  def getStatus = params.get("status") getOrElse "unknown"

  def getGender = params.get("gender") getOrElse "unknown"

  def getProbability = params.get("probability") getOrElse "unknown"
  
  def getName = params.get("name") getOrElse "unknown"
  
  def getProgSkill: Int = {
    val skill = params.get("progSkill") getOrElse "0"
    Integer.parseInt(skill.toString())
    
/*    try {
      Integer.parseInt(params.get("progSkill").toString())
    } catch {
      case _ => 0
    }
*/  }

  def setStatus(newStatus: Status) {
    params += ("status" -> newStatus.toString())
  }
  
  def setName(name: String) {
    params += ("name" -> name)
  }
  
  def setProgSkill(level: Int) {
	params += ("progSkill" -> level)
  }
  
  def die() {
    setStatus(Status.R)
    println("Actor: " + id + "  with status " + getStatus + " and gender " +
            getGender + " just died");
  }

  def heal() {
    setStatus(Status.S)
    println("Actor: " + id + "  with status " + getStatus + " and gender " +
            getGender + " just healed");
  }

  def infect() {
    setStatus(Status.I)
    println("Actor: " + id + "  with status " + getStatus + " and gender " +
            getGender + " just got infected");
  }
  
  def incProgSkill() {
    setProgSkill(getProgSkill + 10)
  }
}

object VirusActor {
  def apply(id: String, params: Map[String, Any]) =
    new VirusActor(id, params)
}
