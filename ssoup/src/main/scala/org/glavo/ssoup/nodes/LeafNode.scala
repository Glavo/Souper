package org.glavo.ssoup.nodes

import org.jsoup.{nodes => js}

private[nodes] abstract class LeafNode extends Node {

}

object LeafNode {
  def apply(node: js.Node): LeafNode = node match {
    case comment: js.Comment => Comment(comment)
    case data: js.DataNode => DataNode(data)
    case docType: js.DocumentType => DocumentType(docType)
    case text: js.TextNode => TextNode(text)
    case xml: js.XmlDeclaration => XmlDeclaration(xml)
  }
}