package org.glavo.souper.select

import scala.collection.JavaConverters._

import org.glavo.souper.nodes._
import org.jsoup.select.{Collector => JCollector, Selector => JSelector}

trait Selector {
  def select(query: String, root: Element): Elements

  def select(query: String, roots: Iterable[Element]): Elements
}

object Selector {
  type SelectorParseException = JSelector.SelectorParseException

  implicit val defaultSelector: Selector = CssSelector

  def select(query: String, root: Element)(implicit selector: Selector): Elements =
    selector.select(query, root)

  def select(evaluator: Evaluator, root: Element): Elements =
    JCollector.collect(evaluator.asJsoup, root.asJsoup).asSouper
}

object CssSelector extends Selector {
  def select(query: String, root: Element): Elements = JSelector.select(query, root.asJsoup).asSouper

  override def select(query: String, roots: Iterable[Element]): Elements =
    JSelector.select(query, roots.map(_.asJsoup).asJava).asSouper
}
