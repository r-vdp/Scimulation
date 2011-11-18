package core.persistence

import xml.XML
import core.graph.{Edge, Vertex, Graph}

/**
 * Repository for storing and loading graphs from XML files.
 *
 * Created by Ramses de Norre
 * Date: 04/11/11
 * Time: 17:26
 */
object GraphRepository {

  def persistGraph[V <: Vertex[V], E <: Edge[V]]
      (graph: Graph[V, E], file: String) {
    XML.save(file, graph.toXML, "UTF-8", true, null)
  }

  def getGraph[V <: Vertex[V], E <: Edge[V]](file: String): Graph[V, E] = {
    val node = XML.loadFile(file)
    val vertices = (node \ "vertices" \ "vertex") map Vertex.fromXML[V]
    val vertexMap: Map[String, V] = getVertexMap[V](vertices)
    val edges = (node \ "edges" \ "edge") map Edge.fromXML[V, E](vertexMap)
    Graph.fromXML[V, E]((node \ "class").text, vertices, edges)
  }

  private[this] def getVertexMap[V <: Vertex[V]](vertices: Seq[V]) = {
    var map = Map.empty[String, V]
    vertices foreach {v => map += (v.id -> v)}
    map
  }
  def loadGraph[V <: Vertex[V], E <: Edge[V]](graph:Graph[V,E],file: String) = {
    val node = XML.loadFile(file)
    val vertices = (node \ "vertices" \ "vertex") map Vertex.fromXML[V]
    val vertexMap: Map[String, V] = getVertexMap[V](vertices)
    val edges = (node \ "edges" \ "edge") map Edge.fromXML[V, E](vertexMap)
    graph.addVertices(vertices)
    graph.addEdges(edges)
  }

}
