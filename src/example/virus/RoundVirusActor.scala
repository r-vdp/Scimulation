package example.virus

import scala.collection.mutable.Map
import example.virus.Status._
import core.graph.Vertex
import engine.Action
import engine.Multiverse
import core.visualize.Color

// zouden graag with action[] eruit trekken zodat de TurnbasedVirusSimulation
// gebruiker een nieuw kind maakt die extend virusactor with Action
// idem voor roundVirusactor, die dan grotendeels zou wegsmelten
// hadden errors met overerving
class RoundVirusActor(inId: String, inMap: Map[String, Any])
  extends Vertex[RoundVirusActor] with Multiverse[RoundVirusActor] with
    Action[RoundVirusActor] with Ordered[RoundVirusActor]with Color{

  override def calcPrior = 0

  def compare(that: RoundVirusActor) = this.calcPrior compare that.calcPrior

  override def color: String = {
    if (getStatus == Status.I.toString()) {
    	"#00ff00";
    } else if (getGender == Gender.Female.toString()) {
      "#0000ff";
    }else{
      "#ff0000"
    }
  }

  object AllDone extends Exception { }

  override def execute() {
    if (getStatus == Status.S.toString()) {
      try{
	      neighbours.foreach{e=>
	        if(e.getStatus==Status.I.toString()){
	          infect();
	          throw AllDone
	        }
	      }
      } catch {
		  case AllDone =>
	  }
      //infect()
    } else if (getStatus == Status.I.toString() && getGender == Gender.Female.toString()) {
      heal()
    }
  }

  override lazy val id = inId
  override lazy val params = inMap

  def getStatus = params.get("status") getOrElse "unknown"

  def getGender = params.get("gender") getOrElse "unknown"

  def getProbability = params.get("probability") getOrElse "unknown"

  def setStatus(newStatus: Status) {
    params += ("status" -> newStatus.toString())
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

object RoundVirusActor {
  def apply(id: String, params: Map[String, Any]) =
    new RoundVirusActor(id, params)
}
