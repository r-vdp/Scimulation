package core.graph.persistence

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import core.graph.{TestEdge, BaseVertex, DirectedGraph}
import core.persistence.GraphRepository

/**
 * Created by Ramses de Norre
 * Date: 05/11/11
 * Time: 11:26
 */
@RunWith(classOf[JUnitRunner])
class XMLTest extends FunSuite {

  import core.graph.GraphTestUtils._

  test("parse XML"){
    val xml =
      <vertex>
        <class>core.graph.BaseVertex</class>
        <id>testXml</id>
        <attr><name>attr1</name><value>val1</value></attr>
      </vertex>

    assert((xml \ "class").text === "core.graph.BaseVertex")
    assert((xml \ "id").text === "testXml")
    val attr = xml \ "attr"
    assert((attr \ "name").text === "attr1")
    assert((attr \ "value").text === "val1")
  }

  test("persist") {
    val graph = new DirectedGraph[BaseVertex, TestEdge]

    val v1 = BaseVertex("first")
    val v2 = BaseVertex("second")
    val v3 = BaseVertex("third")
    val v4 = BaseVertex("fourth")
    val vertices = v1 :: v2 :: v3 :: v4 :: Nil

    val e1 = TestEdge(v1, v2, 5)
    val e2 = TestEdge(v1, v3, 2)
    val e3 = TestEdge(v4, v2, 5)
    val e4 = TestEdge(v2, v3, 5)
    val e5 = TestEdge(v2, v4, 5)
    val edges = e1 :: e2 :: e3 :: e4 :: e5 :: Nil

    graph.addVertices(vertices)
    graph.addEdges(edges)

    val path = "/tmp/graphTest.xml"
    GraphRepository.persistGraph(graph, path)

    val loaded = GraphRepository.loadGraph[BaseVertex, TestEdge](path)

    assert(loaded.size === vertices.size)
    assert(loaded.edges.size === edges.size)
    assert(vertices forall (loaded contains))
    assert(edges forall (loaded contains))

    assert(sameElements(loaded neighbours v1, v2 :: v3 :: Nil))
    assert(sameElements(loaded neighbours v2, v3 :: v4 :: Nil))
    assert(sameElements(loaded neighbours v3, Nil))
    assert(sameElements(loaded neighbours v4, v2 :: Nil))

    assert(sameElements(v1.neighbours, loaded neighbours v1))
    assert(sameElements(v2.neighbours, loaded neighbours v2))
    assert(sameElements(v3.neighbours, loaded neighbours v3))
    assert(sameElements(v4.neighbours, loaded neighbours v4))
  }

  ignore("parse file"){
    val xml =
      <graph>
        <vertices>
          <vertex>
            <class>core.graph.BaseVertex</class>
            <id>fourth</id>

          </vertex> <vertex>
          <class>core.graph.BaseVertex</class>
          <id>root</id>

        </vertex> <vertex>
          <class>core.graph.BaseVertex</class>
          <id>fifth</id>

        </vertex> <vertex>
          <class>core.graph.BaseVertex</class>
          <id>second</id>

        </vertex> <vertex>
          <class>core.graph.BaseVertex</class>
          <id>third</id>

        </vertex>
        </vertices>
        <edges>
          <edge>
            <class>core.BaseEdge</class>
            <from>second</from>
            <to>fourth</to>
            <weight>1.0</weight>
          </edge> <edge>
          <class>core.BaseEdge</class>
          <from>second</from>
          <to>root</to>
          <weight>1.0</weight>
        </edge> <edge>
          <class>core.BaseEdge</class>
          <from>third</from>
          <to>second</to>
          <weight>1.0</weight>
        </edge> <edge>
          <class>core.BaseEdge</class>
          <from>root</from>
          <to>third</to>
          <weight>1.0</weight>
        </edge> <edge>
          <class>core.BaseEdge</class>
          <from>root</from>
          <to>fifth</to>
          <weight>1.0</weight>
        </edge> <edge>
          <class>core.BaseEdge</class>
          <from>third</from>
          <to>fourth</to>
          <weight>1.0</weight>
        </edge>
        </edges>
        <class>core.graph.UndirectedGraph</class>
      </graph>

    println((xml \ "vertices" \ "vertex") foreach {n => println("some: " + n)})
  }
}
