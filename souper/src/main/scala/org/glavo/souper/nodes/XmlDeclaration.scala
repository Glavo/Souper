package org.glavo.souper.nodes

import org.jsoup.{nodes => js}

final class XmlDeclaration private(override val asJsoup: js.XmlDeclaration) extends LeafNode {
  def name: String = asJsoup.name()

  def wholeDeclaration: String = asJsoup.getWholeDeclaration
}

object XmlDeclaration {
  def apply(xml: js.XmlDeclaration): XmlDeclaration = if (xml == null) null else new XmlDeclaration(xml)

  def apply(name: String, isProcessingInstruction: Boolean): XmlDeclaration =
    new XmlDeclaration(new js.XmlDeclaration(name, isProcessingInstruction))
}
