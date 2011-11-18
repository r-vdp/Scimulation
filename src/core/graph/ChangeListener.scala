package core.graph

/**
 * Trait that should be mixed in by objects interested in receiving events
 * from a Graph.
 *
 * Created by Ramses de Norre
 * Date: 27/10/11
 * Time: 10:33
 */

trait ChangeListener {
  def handleChange()
}
