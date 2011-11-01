package core.graph

/**
 * Created by Ramses de Norre
 * Date: 01/11/11
 * Time: 13:04
 */
class TestEdge(from: BaseVertex, to: BaseVertex, weight: Double = 1)
    extends Edge(from, to, weight) {

  protected def construct(from: BaseVertex, to: BaseVertex, weight: Double) =
    TestEdge(from, to, weight)
}

object TestEdge {
  def apply(from: BaseVertex, to: BaseVertex, weight: Double = 1) =
    new TestEdge(from, to, weight)
}
