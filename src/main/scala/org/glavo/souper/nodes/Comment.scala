package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

final class Comment(override val delegate: jn.Comment) extends LeafNode {
  def data: String = delegate.getData
}

object Comment {
  def apply(comment: jn.Comment): Comment = if(comment == null) null else new Comment(comment)

  def apply(data: String): Comment = new Comment(new jn.Comment(data))
}
