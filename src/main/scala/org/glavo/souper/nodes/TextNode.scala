package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

final class TextNode(override val delegate: jn.TextNode) extends LeafNode {
  def text: String = delegate.text()

  def text(text: String): TextNode = TextNode(delegate.text(text))

  def wholeText: String = delegate.getWholeText

  def isBlank: Boolean = delegate.isBlank

  def splitText(offset: Int) = TextNode(delegate.splitText(offset))


}

object TextNode {
  def apply(text: jn.TextNode): TextNode = if (text == null) null else new TextNode(text)

  def apply(text: String): TextNode = new TextNode(new jn.TextNode(text))

  def createFromEncoded(encodedText: String): TextNode =
    TextNode(jn.TextNode.createFromEncoded(encodedText))
}

