package org.glavo.souper.nodes

import org.jsoup.{nodes => js}

final class Comment private(override val asJsoup: js.Comment) extends LeafNode {
  def data: String = asJsoup.getData
}

object Comment {
  def apply(comment: js.Comment): Comment = new Comment(comment)

  def apply(data: String): Comment = Comment(new js.Comment(data))
}
