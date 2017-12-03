package org.glavo.souper.nodes

import org.jsoup.{nodes => js}

final class XmlDeclaration(override val asJsoup: js.XmlDeclaration) extends LeafNode {
  def name: String = asJsoup.name()
}

object XmlDeclaration {
  def apply(xml: js.XmlDeclaration): XmlDeclaration = new XmlDeclaration(xml)
}
