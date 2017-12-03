package org.glavo.souper.nodes

import org.jsoup.{nodes => js}

final class Document(override val asJsoup: js.Document) extends Element(asJsoup) {

}

object Document {
  def apply(doc: js.Document): Document = new Document(doc)
}