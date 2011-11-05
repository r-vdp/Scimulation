package example.virus

import scala.collection.mutable.Map

import core.graph.Vertex
import engine.Action

object Status extends Enumeration {
  type Status = Value
  val S, NI, I, R = Value
}

object Gender extends Enumeration {
  type Gender = Value
  val Male, Female = Value
}

import example.virus.Status._

class VirusActor(id: String, map: Map[String, Any])
  extends Vertex(id) with Action[VirusActor] {

  override def isAble: Boolean = true

  override def execute() {
    if (getStatus == Status.S) {
      infect()
    } else if (getStatus == Status.I && getGender == Gender.Female) {
      heal()
    }
  }

  override val params: Map[String, Any] = map

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
