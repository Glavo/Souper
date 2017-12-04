package org.glavo.souper.select

import org.glavo.souper.nodes.Node
import org.jsoup.{nodes => jn}

trait NodeVisitor extends org.jsoup.select.NodeVisitor {
  def head(node: Node, depth: Int): Unit

  def tail(node: Node, depth: Int): Unit

  override def head(node: jn.Node, depth: Int): Unit = head(Node(node), depth)

  override def tail(node: jn.Node, depth: Int): Unit = tail(Node(node), depth)
}

