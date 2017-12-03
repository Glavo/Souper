package org.glavo.ssoup.nodes

import org.jsoup.{nodes => js}

class Element private[nodes](override val asJsoup: js.Element) extends Node {
}

object Element {
  def apply(elem: js.Element): Element = elem match { //todo
    case doc: js.Document => new Document(doc)
    case elem: js.Element => new Element(elem)
  }
}
