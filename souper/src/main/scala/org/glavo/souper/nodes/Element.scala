package org.glavo.souper.nodes

import java.util
import java.util.regex.Pattern

import org.glavo.souper.parser.Tag
import org.glavo.souper.select.{Elements, Evaluator}
import org.jsoup.{nodes => jn}

import scala.collection.{immutable, mutable}
import scala.collection.JavaConverters._
import scala.util.matching.Regex

class Element protected(override val asJsoup: jn.Element) extends Node {
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
    asJsoup.insertChildren(index, new util.AbstractCollection[jn.Node] {
      override def iterator(): util.Iterator[jn.Node] = new util.Iterator[jn.Node] {
        private val it = children.iterator

        override def next(): jn.Node = it.next().asJsoup

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

  def getElementsByAttributeValueMatching(key: String, regex: Regex): Elements =
    Elements(asJsoup.getElementsByAttributeValueMatching(key, regex.pattern))

  def getElementsByAttributeValueMatching(key: String, pattern: Pattern): Elements =
    Elements(asJsoup.getElementsByAttributeValueMatching(key, pattern))

  def getElementsByAttributeValueMatching(key: String, regex: String): Elements =
    Elements(asJsoup.getElementsByAttributeValueMatching(key, regex))

  def getElementsByIndexLessThan(index: Int): Elements =
    Elements(asJsoup.getElementsByIndexLessThan(index))

  def getElementsByIndexGreaterThan(index: Int): Elements =
    Elements(asJsoup.getElementsByIndexGreaterThan(index))

  def getElementsByIndexEquals(index: Int): Elements =
    Elements(asJsoup.getElementsByIndexEquals(index))

  def getElementsContainingText(searchText: String): Elements =
    Elements(asJsoup.getElementsContainingText(searchText))

  def getElementsContainingOwnText(searchText: String): Elements =
    Elements(asJsoup.getElementsContainingOwnText(searchText))

  def getElementsMatchingText(regex: Regex): Elements =
    Elements(asJsoup.getElementsMatchingText(regex.pattern))

  def getElementsMatchingText(pattern: Pattern): Elements =
    Elements(asJsoup.getElementsMatchingText(pattern))

  def getElementsMatchingText(regex: String): Elements =
    Elements(asJsoup.getElementsMatchingText(regex))

  def getElementsMatchingOwnText(regex: Regex): Elements =
    Elements(asJsoup.getElementsMatchingOwnText(regex.pattern))

  def getElementsMatchingOwnText(pattern: Pattern): Elements =
    Elements(asJsoup.getElementsMatchingOwnText(pattern))

  def getElementsMatchingOwnText(regex: String): Elements =
    Elements(asJsoup.getElementsMatchingOwnText(regex))

  def allElements: Elements = Elements(asJsoup.getAllElements)

  def getAllElements: Elements = Elements(asJsoup.getAllElements)

  def text: String = asJsoup.text()

  def text_=(text: String): Unit = asJsoup.text(text)

  def text(text: String): Element.this.type = {
    asJsoup.text(text)
    this
  }

  def wholeText: String = asJsoup.wholeText()

  def ownText: String = asJsoup.ownText()

  def hasText: Boolean = asJsoup.hasText

  def data: String = asJsoup.data()

  def className: String = asJsoup.className()

  def classNames: mutable.Set[String] = asJsoup.classNames().asScala

  def classNames_=(classNames: mutable.Set[String]): Unit = asJsoup.classNames(classNames.asJava)

  def classNames(classNames: mutable.Set[String]): Element.this.type = {
    asJsoup.classNames(classNames.asJava)
    this
  }

  def hasClass(className: String): Boolean = asJsoup.hasClass(className)

  def addClass(className: String): Element.this.type = {
    asJsoup.addClass(className)
    this
  }

  def removeClass(className: String): Element.this.type = {
    asJsoup.removeClass(className)
    this
  }

  def toggleClass(className: String): Element.this.type = {
    asJsoup.toggleClass(className)
    this
  }

  def value: String = asJsoup.`val`()

  def value_=(value: String): Unit = asJsoup.`val`(value)

  def value(value: String): Element.this.type = {
    asJsoup.`val`(value)
    this
  }

  def html: String = asJsoup.html()

  def html(html: String): Element.this.type = {
    asJsoup.html(html)
    this
  }
}

object Element {
  def apply(elem: jn.Element): Element = elem match {
    case null => null
    case doc: jn.Document => Document(doc)
    case form: jn.FormElement => FormElement(form)
    case pseudo: jn.PseudoTextElement => PseudoTextElement(pseudo)
    case _ => new Element(elem)
  }

  def apply(tag: Tag, baseUri: String, attributes: Attributes = null): Element =
    Element(new jn.Element(tag.asJsoup, baseUri, if (attributes == null) null else attributes.asJsoup))

  def apply(tag: String): Element = new Element(new jn.Element(tag))
}
