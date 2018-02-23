package org.glavo.souper.nodes

import org.glavo.souper.parser._
import org.jsoup.{nodes => jn}

class PseudoTextElement(override val delegate: jn.PseudoTextElement) extends Element(delegate)

object PseudoTextElement {
  def apply(pseudo: jn.PseudoTextElement): PseudoTextElement = new PseudoTextElement(pseudo)

  def apply(tag: Tag, baseUri: String, attributes: Attributes): PseudoTextElement =
    new PseudoTextElement(new jn.PseudoTextElement(tag, baseUri, attributes.delegate))
}
