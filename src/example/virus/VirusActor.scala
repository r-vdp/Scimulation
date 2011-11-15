package example.virus

import scala.collection.mutable.Map
import engine.Action
import core.graph.Vertex

object Status extends Enumeration {
  type Status = Value
  val S, NI, I, R = Value
}

object Gender extends Enumeration {
  type Gender = Value
  val Male, Female = Value
}

import example.virus.Status._
import core.graph.visualize.Color

// zouden graag with action[] eruit trekken zodat de TurnbasedVirusSimulation
// gebruiker een nieuw kind maakt die extend virusactor with Action
// idem voor roundVirusactor, die dan grotendeels zou wegsmelten
// hadden errors met overerving
class VirusActor(inId: String, inMap: Map[String, Any])
  extends Vertex[VirusActor] with Action[VirusActor] with Color {

  override lazy val id = inId
  override lazy val params = inMap

  override def color: String = {
    if (getStatus == Status.I) {
    	"#00ff00";
    } else if (getGender == Gender.Female) {
      "#0000ff";
    }else{
      "#ff0000"
    }
  }

  object AllDone extends Exception { }

  override def execute() {
    if (getStatus == Status.S) {
      try{
	      neighbours.foreach{e=>
	        if(e.getStatus==Status.I){
	          infect();
	          throw AllDone
	        }
	      }
      } catch {
		  case AllDone =>
	  }
      //infect()
    } else if (getStatus == Status.I && getGender == Gender.Female) {
      heal()
    }
  }

  def getStatus = params.get("status") getOrElse "unknown"

  def getGender = params.get("gender") getOrElse "unknown"

  def getProbability = params.get("probability") getOrElse "unknown"

  def setStatus(newStatus: Status) {
    params += ("status" -> newStatus)
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
}

object VirusActor {
  def apply(id: String, params: Map[String, Any]) =
    new VirusActor(id, params)
}
