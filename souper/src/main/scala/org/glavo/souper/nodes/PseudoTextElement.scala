package org.glavo.souper.nodes

import org.glavo.souper.parser._
import org.jsoup.{nodes => jn}

class PseudoTextElement private(override val asJsoup: jn.PseudoTextElement) extends Element(asJsoup)

object PseudoTextElement {
  def apply(pseudo: jn.PseudoTextElement): PseudoTextElement = new PseudoTextElement(pseudo)

  def apply(tag: Tag, baseUri: String, attributes: Attributes): PseudoTextElement =
    new PseudoTextElement(new jn.PseudoTextElement(tag, baseUri, attributes.asJsoup))
}
