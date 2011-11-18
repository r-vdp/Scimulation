package example.social
import engine.Action
import core.graph.Vertex
import core.visualize.Color
import scala.collection.mutable.Map

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

class SocialActor(inId: String, inMap: Map[String, Any])
  extends Vertex[SocialActor] with Color {

  override lazy val id = inId
  override lazy val params = inMap

  override def color: String = {
    "#" + (255 - this.neighbours.size*25).toHexString + "ff" + (255 - this.neighbours.size*25).toHexString
  }

  object AllDone extends Exception { }

  def getGender = params.get("gender") getOrElse "unknown"

  def getCoolFactor = params.get("coolFactor") getOrElse "unknown"
  
  def getName = params.get("name") getOrElse "unknown"
   
}

object SocialActor {
  def apply(id: String, params: Map[String, Any]) =
    new SocialActor(id, params)
}