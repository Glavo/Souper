package org.glavo.souper

import org.glavo.souper.nodes.Element
import org.jsoup
import org.jsoup.{select => js}

package object select {
  implicit final class RichElements(val asJsoup: js.Elements) extends AnyVal {
    @inline
    def asSouper: Elements = Elements(asJsoup)
  }


  implicit final class RichEvaluator(val asJsoup: js.Evaluator) extends AnyVal {
    def asSouper: Evaluator = asJsoup match {
      case s: Evaluator.AsJsoup => s.evaluator
      case _ => new Evaluator {
        override def matches(root: Element, element: Element): Boolean =
          asJsoup.matches(root.asJsoup, element.asJsoup)

        override def asJsoup: jsoup.select.Evaluator = RichEvaluator.this.asJsoup
      }

    }
  }

}
