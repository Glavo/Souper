package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

final class XmlDeclaration private(override val asJsoup: jn.XmlDeclaration) extends LeafNode {
  def name: String = asJsoup.name()

  def wholeDeclaration: String = asJsoup.getWholeDeclaration
}

object XmlDeclaration {
  def apply(xml: jn.XmlDeclaration): XmlDeclaration = if (xml == null) null else new XmlDeclaration(xml)

  def apply(name: String, isProcessingInstruction: Boolean): XmlDeclaration =
    new XmlDeclaration(new jn.XmlDeclaration(name, isProcessingInstruction))
}
