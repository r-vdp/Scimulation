package core.graph

/**
 * Created by Ramses de Norre
 * Date: 01/11/11
 * Time: 13:04
 */
case class TestEdge(from: BaseVertex, to: BaseVertex, weight: Double = 1)
    extends Edge[BaseVertex] {

  type This = TestEdge

  protected def construct(from: BaseVertex, to: BaseVertex, weight: Double) =
    TestEdge(from, to, weight)
}
