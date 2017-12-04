package org.glavo.souper.nodes

import org.jsoup.{nodes => js}

final class TextNode private(override val asJsoup: js.TextNode) extends LeafNode {
  def text: String = asJsoup.text()

  def text(text: String): TextNode = TextNode(asJsoup.text(text))

  def wholeText: String = asJsoup.getWholeText

  def isBlank: Boolean = asJsoup.isBlank

  def splitText(offset: Int) = TextNode(asJsoup.splitText(offset))


}

object TextNode {
  def apply(text: js.TextNode): TextNode = if (text == null) null else new TextNode(text)

  def apply(text: String): TextNode = new TextNode(new js.TextNode(text))

  def createFromEncoded(encodedText: String): TextNode =
    TextNode(js.TextNode.createFromEncoded(encodedText))
}

