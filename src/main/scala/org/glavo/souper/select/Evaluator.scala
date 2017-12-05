package org.glavo.souper.select

import org.glavo.souper.nodes._
import org.jsoup.{nodes => jn, select => js}

trait Evaluator {
  def matches(root: Element, element: Element): Boolean

  def asJsoup: org.jsoup.select.Evaluator = new Evaluator.AsJsoup(this)
}

object Evaluator {
  private[select] final class AsJsoup(val evaluator: Evaluator) extends org.jsoup.select.Evaluator {
    override def matches(root: jn.Element, element: jn.Element): Boolean = {
      evaluator.matches(root.asSouper, element.asSouper)
    }

    override def toString: String = evaluator.toString
  }

  def tag(tagName: String): Evaluator = new js.Evaluator.Tag(tagName).asSouper

  def tagEndsWith(tagName: String): Evaluator = new js.Evaluator.TagEndsWith(tagName).asSouper

  def id(id: String): Evaluator = new js.Evaluator.Id(id).asSouper

  //todo
}
