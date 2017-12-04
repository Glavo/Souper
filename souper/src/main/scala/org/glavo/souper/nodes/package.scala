package org.glavo.souper

import org.jsoup.{nodes => jn}

package object nodes {
  implicit final class RichNode(val node: jn.Node) extends AnyVal {
    def asSouper: Node = Node(node)
  }
}
