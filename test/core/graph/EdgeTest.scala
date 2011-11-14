package core.graph

import org.scalatest.{BeforeAndAfterAll, FunSuite}

/**
 * Created by Ramses de Norre
 * Date: 31/10/11
 * Time: 18:33
 */
class EdgeTest extends FunSuite with BeforeAndAfterAll {

  var v1: BaseVertex = _
  var v2: BaseVertex = _
  var e: TestEdge = _

  override protected def beforeAll() {
    v1 = BaseVertex("1")
    v2 = BaseVertex("2")
    e = TestEdge(v1, v2)
  }

  test("Edges have length 2") {
    assert(e.length === 2)
    assert(e.size === 2)
  }

  test("Test other()") {
    assert((e other v1) === Some(v2))
    assert((e other v2) === Some(v1))
    assert((e other BaseVertex("none")) === None)
  }

  test("Test apply()") {
    assert(e(0) === v1)
    assert(e(1) === v2)
    intercept[IndexOutOfBoundsException] {
      e(2)
    }
  }

  test("Iterator") {
    val it1 = e.iterator
    val it2 = e.iterator

    var count = 0
    e foreach {_ => count += 1}
    assert(count === 2)

    assert(it1.length === 2)
    assert(it2.hasNext)
    assert(it2.next() === v1)
    assert(it2.next() === v2)
    assert(!it2.hasNext)
  }

  test("Compare") {
    val e1 = TestEdge(v1, v2, 1)
    val e2 = TestEdge(v1, v2, 2)
    val e3 = TestEdge(v1, v2, 2)

    assert(e1 < e2)

    assert(e1 <= e2)
    assert(e3 <= e2)

    assert(e2 > e1)

    assert(e2 >= e1)
    assert (e3 >= e2)

    assert(e1 != e2)
    assert(e3 === e2)
  }

  test("Equals") {
    type V = BaseVertex

    assert(TestEdge(v1, v2, 13) == TestEdge(v1, v2, 13))
    assert(TestEdge(v1, v2, 10) != TestEdge(v1, v2, 13))
    assert(TestEdge(v2, v1, 13) != TestEdge(v1, v2, 13))
    assert(TestEdge(v1, v2, 10) == new Edge[V] {
      val from = v1
      val to = v2
      val weight = 10d
      type This = Edge[V]

      protected def construct(from: V, to: V, weight: Double) = null
    })
    assert(TestEdge(v1, v2, 10) != new Edge[V] {
      val from = v2
      val to = v2
      val weight = 10d
      type This = Edge[V]

      protected def construct(from: V, to: V, weight: Double) = null
    })

    /*
     * Beware! Definition of equals() in TestEdge2 breaks reflexivity
     */
    assert(TestEdge(v1, v2, 10) == TestEdge2(v1, v2, 10, 3))
    assert(TestEdge2(v1, v2, 10, 3) != TestEdge(v1, v2, 10))
  }

  case class TestEdge2(from: BaseVertex, to: BaseVertex, weight: Double,
                       extra: Int) extends Edge[BaseVertex] {
    type This = TestEdge2

    protected def construct(from: BaseVertex, to: BaseVertex,
                            weight: Double) = null

    override def equals(that: Any) = that match {
      case TestEdge2(`from`, `to`, `weight`, `extra`) => true
      case _ => false
    }
  }

  test("Reverse") {
    val er = e.reverse
    assert(er(0) === e(1))
    assert(er(1) === e(0))
    assert(er.weight === e.weight)
  }
}
