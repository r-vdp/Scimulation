package monitor
import core.graph.{Graph, Edge, Vertex}
import scala.collection.mutable.ArrayBuffer

trait Publisher[V<:Vertex[V],E<:Edge[V]] {

  val list = new ArrayBuffer[Any with Subscriber[V,E]]

  def publish(g:Graph[V,E]);

  def subscribe(a : Any with Subscriber[V,E])={
    list += a;
  }
}
