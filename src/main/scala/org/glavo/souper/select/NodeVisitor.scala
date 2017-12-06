package org.glavo.souper.select

import org.glavo.souper.nodes.Node
import org.jsoup.{nodes => jn}

/** Node visitor interface. Provide an implementing class to `NodeTraversor` to iterate through nodes.
  *
  * This interface provides two methods, `head` and `tail`. The head method is called when the node is first
  * seen, and the tail method when all of the node's children have been visited. As an example, head can be used to
  * create a start tag for a node, and tail to create the end tag.
  */
trait NodeVisitor extends org.jsoup.select.NodeVisitor {

  /** Callback for when a node is first visited.
    *
    * @param node  the node being visited.
    * @param depth the depth of the node, relative to the root node. E.g., the root node has depth 0, and a child node
    *              of that will have depth 1.
    */
  def head(node: Node, depth: Int): Unit

  /** Callback for when a node is last visited, after all of its descendants have been visited.
    *
    * @param node  the node being visited.
    * @param depth the depth of the node, relative to the root node. E.g., the root node has depth 0, and a child node
    *              of that will have depth 1.
    */
  def tail(node: Node, depth: Int): Unit

  override def head(node: jn.Node, depth: Int): Unit = head(Node(node), depth)

  override def tail(node: jn.Node, depth: Int): Unit = tail(Node(node), depth)
}

