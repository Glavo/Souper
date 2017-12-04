package org.glavo.souper.nodes

import org.glavo.souper.parser._
import org.jsoup.{nodes => js}

class PseudoTextElement private(override val asJsoup: js.PseudoTextElement) extends Element(asJsoup)

object PseudoTextElement {
  def apply(pseudo: js.PseudoTextElement): PseudoTextElement = new PseudoTextElement(pseudo)

  def apply(tag: Tag, baseUri: String, attributes: Attributes): PseudoTextElement =
    new PseudoTextElement(new js.PseudoTextElement(tag, baseUri, attributes.asJsoup))
}
