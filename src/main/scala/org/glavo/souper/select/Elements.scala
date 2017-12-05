package org.glavo.souper.select

import java.util

import org.glavo.souper.nodes.{Element, FormElement}
import org.jsoup.{select => js}

import scala.collection.JavaConverters._
import scala.collection.mutable

class Elements private(val asJsoup: js.Elements) extends mutable.Buffer[Element] {
  def attr(attributeKey: String): String = asJsoup.attr(attributeKey)

  def hasAttr(attributeKey: String): Boolean = asJsoup.hasAttr(attributeKey)

  def eachAttr(attributeKey: String): mutable.Buffer[String] = asJsoup.eachAttr(attributeKey).asScala

  def attr(attributeKey: String, attributeValue: String): Elements.this.type = {
    asJsoup.attr(attributeKey, attributeValue)
    this
  }

  def removeAttr(attributeKey: String): Elements.this.type = {
    asJsoup.removeAttr(attributeKey)
    this
  }

  def addClass(className: String): Elements.this.type = {
    asJsoup.addClass(className)
    this
  }

  def removeClass(className: String): Elements.this.type = {
    asJsoup.removeClass(className)
    this
  }

  def toggleClass(className: String): Elements.this.type = {
    asJsoup.toggleClass(className)
    this
  }

  def value: String = asJsoup.`val`()

  def value_=(value: String): Unit = asJsoup.`val`(value)

  def value(value: String): Elements.this.type = {
    asJsoup.`val`(value)
    this
  }

  def text: String = asJsoup.text()

  def hasText: Boolean = asJsoup.hasText

  def eachText: mutable.Buffer[String] = asJsoup.eachText().asScala

  def html: String = asJsoup.html()

  def outerHtml: String = asJsoup.outerHtml()

  def tagName(tagName: String): Elements.this.type = {
    asJsoup.tagName(tagName)
    this
  }

  def prepend(html: String): Elements.this.type = {
    asJsoup.prepend(html)
    this
  }

  def append(html: String): Elements.this.type = {
    asJsoup.append(html)
    this
  }

  def before(html: String): Elements.this.type = {
    asJsoup.before(html)
    this
  }

  def after(html: String): Elements.this.type = {
    asJsoup.after(html)
    this
  }

  def wrap(html: String): Elements.this.type = {
    asJsoup.wrap(html)
    this
  }

  def unwrap(): Elements.this.type = {
    asJsoup.unwrap()
    this
  }

  def empty(): Elements.this.type = {
    asJsoup.empty()
    this
  }

  def remove(): Elements.this.type = {
    asJsoup.remove()
    this
  }

  // filters

  def \(that: String): Elements = {
    val elements = Elements()
    this.foreach(elem => elements ++= (elem \ that))
    elements
  }

  def css(query: String): Elements = Elements(asJsoup.select(query))

  def not(query: String): Elements = Elements(asJsoup.not(query))

  def eq(index: Int): Elements = Elements(asJsoup.eq(index))

  def is(query: String): Boolean = asJsoup.is(query)

  def next: Elements = Elements(asJsoup.next())

  def next(query: String): Elements = Elements(asJsoup.next(query))

  def nextAll: Elements = Elements(asJsoup.nextAll())

  def nextAll(query: String): Elements = Elements(asJsoup.nextAll(query))

  def prev: Elements = Elements(asJsoup.prev())

  def prev(query: String): Elements = Elements(asJsoup.prev(query))

  def prevAll: Elements = Elements(asJsoup.prevAll())

  def prevAll(query: String): Elements = Elements(asJsoup.prevAll(query))

  def parents: Elements = Elements(asJsoup.parents())

  override def head: Element = Element(asJsoup.first())

  def first: Element = Element(asJsoup.first())

  override def last: Element = Element(asJsoup.last())

  def traverse(nodeVisitor: NodeVisitor): Elements.this.type = {
    asJsoup.traverse(nodeVisitor)
    this
  }

  def filter(nodeFilter: NodeFilter): Elements.this.type = {
    asJsoup.filter(nodeFilter)
    this
  }

  def forms: mutable.Buffer[FormElement] =
    this.filter(_.isInstanceOf[FormElement]).asInstanceOf[mutable.Buffer[FormElement]]

  // Buffer functions

  override def filter(p: Element => Boolean): Elements = {
    val elements = Elements()
    this.foreach(elem => if(p(elem)) elements += elem)
    elements
  }

  override def apply(n: Int): Element = Element(asJsoup.get(n))

  override def update(n: Int, newelem: Element): Unit = asJsoup.set(n, newelem.asJsoup)

  override def length: Int = asJsoup.size()

  override def +=(elem: Element): Elements.this.type = {
    asJsoup.add(elem.asJsoup)
    this
  }

  def +=(html: String): Elements.this.type = {
    asJsoup.append(html)
    this
  }

  override def clear(): Unit = asJsoup.clear()

  override def +=:(elem: Element): Elements.this.type = {
    asJsoup.add(0, elem.asJsoup)
    this
  }

  def +=:(html: String): Elements.this.type = {
    asJsoup.prepend(html)
    this
  }

  override def insertAll(n: Int, elems: Traversable[Element]): Unit =
    asJsoup.addAll(n, elems.map(_.asJsoup).toVector.asJavaCollection)

  override def remove(n: Int): Element = Element(asJsoup.remove(n))


  override def iterator: Iterator[Element] = new Iterator[Element] {
    private val it = asJsoup.iterator()

    override def hasNext: Boolean = it.hasNext

    override def next(): Element = Element(it.next())
  }

  override def clone(): Elements = Elements(asJsoup.clone())

  override def hashCode(): Int = asJsoup.hashCode()

  override def equals(that: Any): Boolean =
    that.isInstanceOf[Elements] && that.asInstanceOf[Elements].asJsoup == asJsoup

  override def toString(): String = asJsoup.toString
}

object Elements {
  def apply(elements: org.jsoup.select.Elements): Elements = new Elements(elements)

  def apply(initialCapacity: Int): Elements = Elements(new js.Elements(initialCapacity))

  def apply(elements: Element*): Elements = Elements(new js.Elements(new util.AbstractCollection[org.jsoup.nodes.Element] {
    override def iterator(): util.Iterator[org.jsoup.nodes.Element] = new util.Iterator[org.jsoup.nodes.Element] {
      private val it = elements.iterator

      override def next(): org.jsoup.nodes.Element = it.next().asJsoup

      override def hasNext: Boolean = it.hasNext
    }

    override def size(): Int = elements.size
  }))
}