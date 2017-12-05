package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

private[nodes] abstract class LeafNode extends Node {

}

object LeafNode {
  def apply(node: jn.Node): LeafNode = node match {
    case null => null
    case comment: jn.Comment => Comment(comment)
    case data: jn.DataNode => DataNode(data)
    case docType: jn.DocumentType => DocumentType(docType)
    case text: jn.TextNode => TextNode(text)
    case xml: jn.XmlDeclaration => XmlDeclaration(xml)
  }
}