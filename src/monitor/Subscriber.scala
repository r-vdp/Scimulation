package monitor

import core.graph.{Graph, Edge, Vertex}

trait Subscriber[V<:Vertex[V],E<:Edge[V]] {

  def update(g:Graph[V,E]);
  
  def subscribeTo(p : Any with Publisher[V,E]){
    p.subscribe(this)
  }
}