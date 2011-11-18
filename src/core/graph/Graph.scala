package core.graph

import traversal.{SimpleGraphTraverser, GraphTraverser}
import xml.NodeBuffer
import core.persistence.GraphBuilder

/**
 * Abstract graph class.
 * Created by Ramses de Norre
 * Date: 31/10/11
 * Time: 13:11
 */
abstract class Graph[V <: Vertex[V], E <: Edge[V]]
  extends Observable with Traversable[V] {

  implicit def set2Seq[A](set: Set[A]): Seq[A] = set.toSeq

  /**
   * The number of vertices in this graph
   */
  override def size: Int

  def deepCopy: Graph[V,E]

  def contains(vertex: V): Boolean
  def contains(edge: E): Boolean

  /**
   * True iff both the vertices referenced by edge are contained in this graph.
   */
  def isLegal(edge: E) = edge forall (this contains)

  def addVertex(vertex: V) {
    if (!contains(vertex)) {
      addVertexImpl(vertex)
      fireChanged()
    }
  }

  protected def addVertexImpl(vertex: V)

  def addVertices(vertices: Seq[V]) {
    if (!(vertices withFilter (!contains(_)) map addVertexImpl).isEmpty) {
      fireChanged()
    }
  }

  def getVertex(id: String): Option[V] = vertices find (_.id == id)

  def addEdge(edge: E) {
    if (isLegal(edge)) {
      addEdgeImpl(edge)
      fireChanged()
    }
  }

  protected def addEdgeImpl(edge: E)

  def addEdges(edges: Seq[E]) {
    if (!((edges withFilter isLegal) map addEdgeImpl).isEmpty) {
      fireChanged()
    }
  }

  def removeEdge(edge: E) {
    if (contains(edge)) {
      removeEdgeImpl(edge)
      fireChanged()
    }
  }

  protected def removeEdgeImpl(edge: E)

  def removeVertex(vertex: V) {
    if (contains(vertex)) {
      removeVertexImpl(vertex)
      assert(!contains(vertex))
      fireChanged()
    }
  }

  protected def removeVertexImpl(vertex: V)

  /**
   * Retrieve a set of all vertices which are reachable from the given vertex.
   */
  def neighbours(vertex: V): Set[V]

  /**
   * The edges connecting the given vertex to its neighbours.
   */
  def neighbourEdges(vertex: V): Set[E]

  def vertices: Seq[V]

  def edges: Seq[E]

  /**
   * Retrieve some vertex that is part of this graph,
   * used to be able to get an entrance point into the graph.
   */
  def someVertex: V

  override def toString: String

  def foreach[U](f: (V) => U) {
    foreachImpl(f)
  }

  def foreach[U](f: (V) => U, traverser: GraphTraverser[V, E]) {
    foreachImpl(f)(traverser)
  }

  /**
   * The default traverser if none is specified.
   */
  protected implicit var traverser: GraphTraverser[V, E] =
    new SimpleGraphTraverser(this)

  def setTraverser(t: GraphTraverser[V, E]) {
    if (traverser.graph == this) {
      traverser = t
    }
  }

  protected def foreachImpl[U](f: (V) => U)
                            (implicit traverser: GraphTraverser[V, E]) {
    traverser foreach f
  }

  def toXML =
    <graph>
      <class>{getClass.getCanonicalName}</class>
      <vertices>
        {verticesToXML}
      </vertices>
      <edges>
        {edgesToXML}
      </edges>
    </graph>

  def verticesToXML = {
    val out = new NodeBuffer
    vertices foreach {out += _.toXML}
    out
  }

  def edgesToXML = {
    val out = new NodeBuffer
    edges foreach {out += _.toXML}
    out
  }
}

object Graph {
  /**
   * Helper function that returns a lambda which can be used to turn a map of
   * vertex -> [edge] mappings into a string.
   */
  private[graph] def stringBuilder[V <: Vertex[V], E <: Edge[V]] = {
    (_: Map[V, Set[E]]) map {
      case (vertex, edges) =>
        vertex + ": " + (edges mkString ", ")
    } mkString "\n"
  }

  def fromXML[V <: Vertex[V], E <: Edge[V]](graphClass: String,
                                         vertices: Seq[V],
                                         edges: Seq[E]): Graph[V, E] = {
    val graph = (new GraphBuilder).create[V, E](graphClass)
    graph.addVertices(vertices)
    graph.addEdges(edges)
    graph
  }
}
