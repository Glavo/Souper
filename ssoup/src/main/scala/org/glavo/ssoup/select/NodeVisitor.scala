package org.glavo.ssoup.select

import org.glavo.ssoup.nodes.Node
import org.jsoup.{nodes => js}

trait NodeVisitor extends org.jsoup.select.NodeVisitor {
  def head(node: Node, depth: Int): Unit

  def tail(node: Node, depth: Int): Unit

  override def head(node: js.Node, depth: Int): Unit = head(Node(node), depth)

  override def tail(node: js.Node, depth: Int): Unit = tail(Node(node), depth)
}

