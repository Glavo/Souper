package org.glavo.ssoup.nodes

import org.glavo.ssoup.select.{NodeFilter, NodeVisitor}
import org.jsoup.{nodes => js}

import scala.collection.mutable
import scala.collection.JavaConverters._

abstract class Node extends Cloneable {
  val asJsoup: js.Node

  def nodeName: String = asJsoup.nodeName()

  def hasParent: Boolean = asJsoup.hasParent

  def attr(attributeKey: String): String = asJsoup.attr(attributeKey)

  def attributes: Attributes = Attributes(asJsoup.attributes())

  def attr(attributeKey: String, attributeValue: String): Node.this.type = {
    asJsoup.attr(attributeKey, attributeValue)
    this
  }

  def hasAttr(attributeKey: String): Boolean = asJsoup.hasAttr(attributeKey)

  def removeAttr(attributeKey: String): Node.this.type = {
    asJsoup.removeAttr(attributeKey)
    this
  }

  def clearAttributes(): Node.this.type = {
    asJsoup.clearAttributes()
    this
  }

  def baseUri: String = asJsoup.baseUri()

  def childNode(index: Int): Node = Node(asJsoup.childNode(index))

  def childNodes: Seq[Node] = new Seq[Node] {
    private val list = asJsoup.childNodes()

    override def length = list.size()

    override def apply(idx: Int) = Node(list.get(idx))

    override def iterator: Iterator[Node] = new Iterator[Node] {
      private val it = list.iterator()

      override def hasNext = it.hasNext

      override def next() = Node(it.next())
    }
  }

  def childNodesCopy: scala.collection.mutable.Buffer[Node] = new Node.ListView(asJsoup.childNodesCopy)

  def childNodeSize: Int = asJsoup.childNodeSize()

  def parent: Node = Node(asJsoup.parent())

  def parentNode: Node = Node(asJsoup.parentNode())

  def root: Node = Node(asJsoup.root())

  def ownerDocument: Document = Document(asJsoup.ownerDocument())

  def remove(): Unit = asJsoup.remove()

  def before(html: String): Node = Node(asJsoup.before(html))

  def before(node: Node): Node = Node(asJsoup.before(node.asJsoup))

  def after(html: String): Node = Node(asJsoup.after(html))

  def after(node: Node): Node = Node(asJsoup.after(node.asJsoup))

  def wrap(html: String): Node = Node(asJsoup.wrap(html))

  def unwrap: Node = Node(asJsoup.unwrap())

  def replaceWith(in: Node): Unit = asJsoup.replaceWith(in.asJsoup)

  def siblingNodes: mutable.Buffer[Node] = new Node.ListView(asJsoup.siblingNodes())

  def nextSibling: Node = Node(asJsoup.nextSibling())

  def previousSibling: Node = Node(asJsoup.previousSibling())

  def siblingIndex: Int = asJsoup.siblingIndex()

  def traverse(nodeVisitor: NodeVisitor): Node = Node(asJsoup.traverse(nodeVisitor))

  def filter(nodeFilter: NodeFilter): Node = Node(asJsoup.filter(nodeFilter))

  def outerHtml: String = asJsoup.outerHtml()

  def html[A <: Appendable](appendable: A): appendable.type = asJsoup.html(appendable)

  override def clone(): Node = Node(asJsoup.clone())

  override def toString: String = asJsoup.toString

  final override def equals(obj: scala.Any): Boolean = obj.isInstanceOf[Node] && obj.asInstanceOf[Node].asJsoup == asJsoup

  final override def hashCode(): Int = asJsoup.hashCode()
}

object Node {
  def apply(node: js.Node): Node = node match {
    case elem: js.Element => Element(elem)
  }

  private class ListView(val list: java.util.List[js.Node]) extends mutable.Buffer[Node] {
    override def apply(n: Int): Node = Node(list.get(n))

    override def update(n: Int, newelem: Node): Unit = list.set(n, newelem.asJsoup)

    override def length: Int = list.size()

    override def +=(elem: Node): ListView.this.type = {
      list.add(elem.asJsoup)
      this
    }

    override def clear(): Unit = list.clear()

    override def +=:(elem: Node): ListView.this.type = {
      if (list.size() == 0) list.add(elem.asJsoup)
      else list.add(0, elem.asJsoup)
      this
    }

    override def insertAll(n: Int, elems: Traversable[Node]): Unit = list.addAll(n, elems.map(_.asJsoup).toSeq.asJavaCollection)

    override def remove(n: Int): Node = Node(list.remove(n))

    override def iterator: Iterator[Node] = new Iterator[Node] {
      private val it = list.iterator()

      override def hasNext: Boolean = it.hasNext

      override def next(): Node = Node(it.next())
    }
  }

}