package org.glavo.ssoup.nodes

import org.glavo.ssoup.parser.Tag
import org.jsoup.{nodes => js}

class Element protected(override val asJsoup: js.Element) extends Node {
  def tagName: String = asJsoup.tagName()

  def tagName(tagName: String): Element = Element(asJsoup.tagName(tagName))

  def tag: Tag = Tag(asJsoup.tag)


}

object Element {
  def apply(elem: js.Element): Element = elem match { //todo
    case doc: js.Document => Document(doc)
    case elem: js.Element => new Element(elem)
  }

  def apply(tag: Tag, baseUri: String, attributes: Attributes = null): Element =
    Element(new js.Element(tag.asJsoup, baseUri, if(attributes == null) null else attributes.asJsoup))

  def apply(tag: String): Element = Element(new js.Element(tag))
}
