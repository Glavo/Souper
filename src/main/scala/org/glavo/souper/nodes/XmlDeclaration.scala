package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

final class XmlDeclaration(override val delegate: jn.XmlDeclaration) extends LeafNode {
  def name: String = delegate.name()

  def wholeDeclaration: String = delegate.getWholeDeclaration
}

object XmlDeclaration {
  def apply(xml: jn.XmlDeclaration): XmlDeclaration = if (xml == null) null else new XmlDeclaration(xml)

  def apply(name: String, isProcessingInstruction: Boolean): XmlDeclaration =
    new XmlDeclaration(new jn.XmlDeclaration(name, isProcessingInstruction))
}
