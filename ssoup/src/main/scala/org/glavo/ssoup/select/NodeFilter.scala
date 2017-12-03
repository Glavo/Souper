package org.glavo.ssoup.select

import org.glavo.ssoup.nodes.Node
import org.jsoup.{nodes => js}
import org.jsoup.select.NodeFilter.{FilterResult => FR}


trait NodeFilter extends org.jsoup.select.NodeFilter {
  def head(node: Node, depth: Int): NodeFilter.FilterResult

  def tail(node: Node, depth: Int): NodeFilter.FilterResult

  override def head(node: js.Node, depth: Int): FR = head(Node(node), depth)

  override def tail(node: js.Node, depth: Int): FR = tail(Node(node), depth)
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