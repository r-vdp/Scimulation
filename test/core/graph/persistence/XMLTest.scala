package core.graph.persistence

import org.scalatest.FunSuite
import org.scalatest.{BeforeAndAfterEach, Tag, FunSuite}
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

/**
 * Created by Ramses de Norre
 * Date: 05/11/11
 * Time: 11:26
 */
@RunWith(classOf[JUnitRunner])
class XMLTest extends FunSuite {

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
