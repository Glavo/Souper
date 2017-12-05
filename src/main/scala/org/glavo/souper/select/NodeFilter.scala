package org.glavo.souper.select

import org.glavo.souper.nodes.Node
import org.jsoup.{nodes => jn}
import org.jsoup.select.NodeFilter.{FilterResult => FR}


trait NodeFilter extends org.jsoup.select.NodeFilter {
  def head(node: Node, depth: Int): NodeFilter.FilterResult

  def tail(node: Node, depth: Int): NodeFilter.FilterResult

  override def head(node: jn.Node, depth: Int): FR = head(Node(node), depth)

  override def tail(node: jn.Node, depth: Int): FR = tail(Node(node), depth)
}

object NodeFilter {
  type FilterResult = FR

  object FilterResult {
    val Continue = FR.CONTINUE

    val SkipChildren = FR.SKIP_CHILDREN

    val SkipEntirely = FR.SKIP_ENTIRELY

    val Remove = FR.REMOVE

    val Stop = FR.STOP
  }

}