package core.graph

import traversal.BreadthFirstTraverser
import org.scalatest.{BeforeAndAfterEach, Tag, FunSuite}
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

/**
 * Created by Ramses de Norre
 * Date: 01/11/11
 * Time: 12:56
 */
@RunWith(classOf[JUnitRunner])
class GraphTest extends FunSuite with BeforeAndAfterEach {

  type G = Graph[BaseVertex, TestEdge]

  var g1: G = _
  var g2: DirectedGraph[BaseVertex, TestEdge] = _
  var g3: G = _
  var g4: UndirectedGraph[BaseVertex, TestEdge] = _

  var v1: BaseVertex = _
  var v2: BaseVertex = _
  var v3: BaseVertex = _
  var v4: BaseVertex = _

  var e1: TestEdge = _
  var e2: TestEdge = _
  var e3: TestEdge = _
  var e4: TestEdge = _

  var vertices: Seq[BaseVertex] = _
  var edges: Seq[TestEdge] = _

  override protected def beforeEach() {
    g1 = new DirectedGraph[BaseVertex, TestEdge]
    g2 = new DirectedGraph[BaseVertex, TestEdge]
    g3 = new UndirectedGraph[BaseVertex, TestEdge]
    g4 = new UndirectedGraph[BaseVertex, TestEdge]

    v1 = BaseVertex("first")
    v2 = BaseVertex("second")
    v3 = BaseVertex("third")
    v4 = BaseVertex("fourth")

    e1 = TestEdge(v1, v2, 2)
    e2 = TestEdge(v2, v3, 5)
    e3 = TestEdge(v4, v1, 1)
    e4 = TestEdge(v2, v1, 10)

    vertices = v1 :: v2 :: v3 :: v4 :: Nil
    edges = e1 :: e2 :: e3 :: e4 :: Nil
  }

  implicit def graphs = g1 :: g2 :: g3 :: g4 :: Nil

  def doG(f: => G => Unit)(implicit graphs: Seq[G]) {
    graphs foreach f
  }

  def testG(testName: String, testTags: Tag*)
           (testFun: => G => Unit) {
    super.test(testName, testTags: _*) {
      doG {
        testFun
      }
    }
  }

  def ignoreG(testName: String, graphs: => Seq[G], testTags: Tag*)
             (testFun: => G => Unit) {
    super.ignore(testName, testTags: _*) {}
  }

  def testG(testName: String, graphs: => Seq[G], testTags: Tag*)
           (testFun: => G => Unit) {
    super.test(testName, testTags: _*) {
      doG {
        testFun
      } (graphs)
    }
  }

  testG("size") { g =>
    assert(g.isEmpty)
    assert(g.size == 0)

    g.addVertex(v1)
    assert(!g.isEmpty)
    assert(g.size == 1)
  }

  testG("contains") { g =>
    g.addVertices(vertices)
    vertices foreach {v => assert(g contains v)}

    g.addEdges(edges)
    edges foreach {e => assert(g contains e)}
  }

  testG("remove vertices") { g =>
    g.addVertices(vertices)
    vertices foreach {v => assert(g contains v)}

    g.addEdges(edges)
    edges foreach {e => assert(g contains e)}

    g.removeVertex(v2)
    assert(!(g contains v2))
    (e1 :: e2 :: e4 :: Nil) foreach {e => assert(!(g contains e))}
  }

  testG("remove edges") { g =>
    g.addVertices(vertices)
    g.addEdges(edges)

    g.removeEdge(e3)
    assert(!(g contains e3))
    assert(g contains v4)
  }

  testG("neighbours, undirected", g3 :: g4 :: Nil) { g =>
    g.addVertices(vertices)
    g.addEdges(edges)

    assert(sameElements(v2 :: v4 :: Nil, g neighbours v1))
    assert(sameElements(v1 :: v3 :: Nil, g neighbours v2))
    assert(sameElements(v2 :: Nil, g neighbours v3))
    assert(sameElements(v1 :: Nil, g neighbours v4))

    assert(sameElements(v2 :: v4 :: Nil, v1.neighbours))
    assert(sameElements(v1 :: v3 :: Nil, v2.neighbours))
    assert(sameElements(v2 :: Nil, v3.neighbours))
    assert(sameElements(v1 :: Nil, v4.neighbours))
  }

  testG("neighbours, directed", g1 :: g2 :: Nil) { g =>
    g.addVertices(vertices)
    g.addEdges(edges)

    assert(sameElements(v2 :: Nil, g neighbours v1))
    assert(sameElements(v1 :: v3 :: Nil, g neighbours v2))
    assert(sameElements(Nil, g neighbours v3))
    assert(sameElements(v1 :: Nil, g neighbours v4))

    assert(sameElements(v2 :: Nil, v1.neighbours))
    assert(sameElements(v1 :: v3 :: Nil, v2.neighbours))
    assert(sameElements(Nil, v3.neighbours))
    assert(sameElements(v1 :: Nil, v4.neighbours))
  }

  testG("vertex neighbours") { g=>
    g.addVertices(vertices)
    g.addEdges(edges)

    assert(sameElements(g neighbours v1, v1.neighbours))
    assert(sameElements(g neighbours v2, v2.neighbours))
    assert(sameElements(g neighbours v3, v3.neighbours))
    assert(sameElements(g neighbours v4, v4.neighbours))
    assert(v1.neighbours(0).isInstanceOf[BaseVertex])
  }

  def sameElements[A](first: Seq[A], sec: Seq[A]) =
    if ((first.size == sec.size) && (first forall (sec contains))) {
      true
    } else {
      println("first: " + first +"\nsecond: " + sec)
      false
    }

  implicit def set2Seq[A](set: Set[A]): Seq[A] = set.toSeq

  testG("traversable") { g =>
    g.addVertices(vertices)
    val vs = (for (v <- g) yield v).toList

    vertices foreach (v => assert(vs contains v))
  }

  test("BF traversal.") {
    g2.addVertices(vertices)
    g2.addEdges(edges)
    g2.setTraverser(new BreadthFirstTraverser(g2))
    val vs = (for (v <- g2) yield v).toSeq
    assert(sameElements(v1 :: v2 :: v3 :: Nil, vs))
  }
}
