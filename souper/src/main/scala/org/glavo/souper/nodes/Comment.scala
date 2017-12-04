package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

final class Comment private(override val asJsoup: jn.Comment) extends LeafNode {
  def data: String = asJsoup.getData
}

object Comment {
  def apply(comment: jn.Comment): Comment = if(comment == null) null else new Comment(comment)

  def apply(data: String): Comment = new Comment(new jn.Comment(data))
}
