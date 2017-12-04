package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

final class TextNode private(override val asJsoup: jn.TextNode) extends LeafNode {
  def text: String = asJsoup.text()

  def text(text: String): TextNode = TextNode(asJsoup.text(text))

  def wholeText: String = asJsoup.getWholeText

  def isBlank: Boolean = asJsoup.isBlank

  def splitText(offset: Int) = TextNode(asJsoup.splitText(offset))


}

object TextNode {
  def apply(text: jn.TextNode): TextNode = if (text == null) null else new TextNode(text)

  def apply(text: String): TextNode = new TextNode(new jn.TextNode(text))

  def createFromEncoded(encodedText: String): TextNode =
    TextNode(jn.TextNode.createFromEncoded(encodedText))
}

