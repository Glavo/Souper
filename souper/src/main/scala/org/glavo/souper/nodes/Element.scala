package org.glavo.souper.nodes

import java.util

import org.glavo.souper.parser.Tag
import org.glavo.souper.select.{Elements, Evaluator}
import org.jsoup.{nodes => js}

import scala.collection.{immutable, mutable}

class Element protected(override val asJsoup: js.Element) extends Node {
  def tagName: String = asJsoup.tagName()

  def tagName_=(tagName: String): Unit = asJsoup.tagName(tagName)

  def tagName(tagName: String): Element.this.type = {
    asJsoup.tagName(tagName)
    this
  }

  def tag: Tag = Tag(asJsoup.tag)

  def isBlock: Boolean = asJsoup.isBlock

  def id: String = asJsoup.id()

  def attr(attributeKey: String, attributeValue: Boolean): Element.this.type = {
    asJsoup.attr(attributeKey, attributeValue)
    this
  }

  def dataset: mutable.Map[String, String] = Attributes(asJsoup.attributes()).dataset

  override final def parent: Element = Element(asJsoup.parent())

  def parents: Elements = Elements(asJsoup.parents())

  def child(index: Int): Element = Element(asJsoup.child(index))

  def children(): Elements = Elements(asJsoup.children())

  def textNodes: scala.collection.immutable.Seq[TextNode] = new immutable.Seq[TextNode] {
    private val list = asJsoup.textNodes()

    override def apply(idx: Int): TextNode = TextNode(list.get(idx))

    override def length: Int = list.size()

    override def iterator: Iterator[TextNode] = new Iterator[TextNode] {
      private val it = list.iterator()

      override def hasNext: Boolean = it.hasNext

      override def next(): TextNode = TextNode(it.next())
    }
  }

  def dataNodes: scala.collection.immutable.Seq[DataNode] = new immutable.Seq[DataNode] {
    private val list = asJsoup.dataNodes()

    override def apply(idx: Int): DataNode = DataNode(list.get(idx))

    override def length: Int = list.size()

    override def iterator: Iterator[DataNode] = new Iterator[DataNode] {
      private val it = list.iterator()

      override def hasNext: Boolean = it.hasNext

      override def next(): DataNode = DataNode(it.next())
    }
  }

  def select(cssQuery: String): Elements = Elements(asJsoup.select(cssQuery))

  def selectFirst(cssQuery: String): Element = Element(asJsoup.selectFirst(cssQuery))

  def is(cssQuery: String): Boolean = asJsoup.is(cssQuery)

  def is(evaluator: Evaluator): Boolean = asJsoup.is(evaluator)

  def appendChild(child: Node): Element.this.type = {
    asJsoup.appendChild(child.asJsoup)
    this
  }

  def appendTo(parent: Element): Element.this.type = {
    asJsoup.appendTo(parent.asJsoup)
    this
  }

  def prependChild(child: Node): Element.this.type = {
    asJsoup.prependChild(child.asJsoup)
    this
  }

  def insertChildren(index: Int, children: Iterable[Node]): Element.this.type = {
    asJsoup.insertChildren(index, new util.AbstractCollection[js.Node] {
      override def iterator(): util.Iterator[js.Node] = new util.Iterator[js.Node] {
        private val it = children.iterator

        override def next(): js.Node = it.next().asJsoup

        override def hasNext: Boolean = it.hasNext
      }

      override lazy val size: Int = {
        children.size
      }
    })
    this
  }

  def insertChildren(index: Int, children: Node*): Element.this.type =
    insertChildren(index, children)

  def appendElement(tagName: String): Element = Element(asJsoup.appendElement(tagName))

  def prependElement(tagName: String): Element = Element(asJsoup.prependElement(tagName))

  def appendText(text: String): Element.this.type = {
    asJsoup.appendText(text)
    this
  }

  def prependText(text: String): Element.this.type = {
    asJsoup.appendText(text)
    this
  }

  def +=(html: String): Element.this.type = {
    asJsoup.append(html)
    this
  }

  def append(html: String): Element.this.type = {
    asJsoup.append(html)
    this
  }

  def +=:(html: String): Element.this.type = {
    asJsoup.prepend(html)
    this
  }

  def prepend(html: String): Element.this.type = {
    asJsoup.prepend(html)
    this
  }

  override def before(html: String): Element = Element(asJsoup.before(html))

  override def before(node: Node): Element = Element(asJsoup.before(node.asJsoup))

  override def after(html: String): Element = Element(asJsoup.after(html))

  override def after(node: Node): Element = Element(asJsoup.after(node.asJsoup))

  def empty(): Element.this.type = {
    asJsoup.empty()
    this
  }

  override def wrap(html: String): Element = Element(asJsoup.wrap(html))

  def cssSelector: String = asJsoup.cssSelector()

  def siblingElements: Elements = Elements(asJsoup.siblingElements())

  def nextElementSibling: Element = Element(asJsoup.nextElementSibling())

  def previousElementSibling: Element = Element(asJsoup.previousElementSibling())

  def firstElementSibling: Element = Element(asJsoup.firstElementSibling())

  def elementSiblingIndex: Int = asJsoup.elementSiblingIndex()

  def lastElementSibling: Element = Element(asJsoup.lastElementSibling())

  // DOM type methods

  def getElementsByTag(tagName: String): Elements = Elements(asJsoup.getElementsByTag(tagName))

  def getElementById(id: String): Element = Element(asJsoup.getElementById(id))

  def getElementsByClass(className: String): Elements = Elements(asJsoup.getElementsByClass(className))

  def getElementsByAttribute(key: String): Elements = Elements(asJsoup.getElementsByAttribute(key))

  def getElementsByAttributeStarting(keyPrefix: String): Elements =
    Elements(asJsoup.getElementsByAttributeStarting(keyPrefix))

  def getElementsByAttributeValue(key: String, value: String): Elements =
    Elements(asJsoup.getElementsByAttributeValue(key, value))

  def getElementsByAttributeValueNot(key: String, value: String): Elements =
    Elements(asJsoup.getElementsByAttributeValueNot(key, value))

  def getElementsByAttributeValueStarting(key: String, valuePrefix: String) =
    Elements(asJsoup.getElementsByAttributeValueStarting(key, valuePrefix))

  def getElementsByAttributeValueEnding(key: String, valueSuffix: String) =
    Elements(asJsoup.getElementsByAttributeValueEnding(key, valueSuffix))

  def getElementsByAttributeValueContaining(key: String, matcher: String): Elements =
    Elements(asJsoup.getElementsByAttributeValueContaining(key, matcher))


}

object Element {
  def apply(elem: js.Element): Element = elem match { //todo
    case null => null
    case doc: js.Document => Document(doc)
    case elem: js.Element => new Element(elem)
  }

  def apply(tag: Tag, baseUri: String, attributes: Attributes = null): Element =
    Element(new js.Element(tag.asJsoup, baseUri, if (attributes == null) null else attributes.asJsoup))

  def apply(tag: String): Element = Element(new js.Element(tag))
}
