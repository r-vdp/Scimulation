package core

/**
 * Created by Ramses de Norre
 * Date: 27/10/11
 * Time: 10:32
 */

trait Observable {
  private var listeners: List[ChangeListener] = Nil

  def addChangeListener(listener: ChangeListener) {
    listeners = listener :: listeners
  }

  def removeChangeListener(listener: ChangeListener) {
    listeners = listeners filter (listener !=)
  }

  def fireChanged() {
    listeners foreach (l => l.handleChange())
  }
}
