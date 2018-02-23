package org.glavo.souper.select

import org.glavo.souper.{SouperDelegate, nodes}
import org.glavo.souper.nodes._
import org.jsoup.{nodes => jn, select => js}

trait Evaluator {

  private[select] final class AsJsoup extends org.jsoup.select.Evaluator {
    def asSouper: Evaluator = Evaluator.this

    override def matches(root: jn.Element, element: jn.Element): Boolean = {
      Evaluator.this.matches(nodes.Implicits.elementWrapper(root), nodes.Implicits.elementWrapper(element))
    }

    override def toString: String = Evaluator.this.toString
  }

  def matches(root: Element, element: Element): Boolean = delegate.matches(root.delegate, element.delegate)

  def delegate: js.Evaluator = new AsJsoup()
}

trait WrappedEvaluator extends Evaluator with SouperDelegate[js.Evaluator]

object Evaluator {

  private[select] final class DefaultEvaluator(override val delegate: js.Evaluator) extends WrappedEvaluator

  def apply(evaluator: js.Evaluator): Evaluator = evaluator match {
    case null => null
    //todo
    case e: Evaluator#AsJsoup => e.asSouper
    case _ => new DefaultEvaluator(evaluator)
  }

  //todo
}
