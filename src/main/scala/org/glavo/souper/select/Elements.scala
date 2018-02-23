package org.glavo.souper.select

import org.glavo.souper.nodes.{Element, FormElement}
import org.glavo.souper.util.TransformationIterator
import org.jsoup.{select => js, nodes => jn}

import scala.collection.JavaConverters._
import scala.collection.mutable

class Elements private(val delegate: js.Elements)
  extends mutable.Buffer[Element]
    with org.glavo.souper.SouperDelegate[js.Elements] {
  def attr(attributeKey: String): String = delegate.attr(attributeKey)

  def hasAttr(attributeKey: String): Boolean = delegate.hasAttr(attributeKey)

  def eachAttr(attributeKey: String): mutable.Buffer[String] = delegate.eachAttr(attributeKey).asScala

  def attr(attributeKey: String, attributeValue: String): Elements.this.type = {
    delegate.attr(attributeKey, attributeValue)
    this
  }

  def removeAttr(attributeKey: String): Elements.this.type = {
    delegate.removeAttr(attributeKey)
    this
  }

  def addClass(className: String): Elements.this.type = {
    delegate.addClass(className)
    this
  }

  def removeClass(className: String): Elements.this.type = {
    delegate.removeClass(className)
    this
  }

  def toggleClass(className: String): Elements.this.type = {
    delegate.toggleClass(className)
    this
  }

  def value: String = delegate.`val`()

  def value_=(value: String): Unit = delegate.`val`(value)

  def value(value: String): Elements.this.type = {
    delegate.`val`(value)
    this
  }

  def text: String = delegate.text()

  def hasText: Boolean = delegate.hasText

  def eachText: mutable.Buffer[String] = delegate.eachText().asScala

  def html: String = delegate.html()

  def outerHtml: String = delegate.outerHtml()

  def tagName(tagName: String): Elements.this.type = {
    delegate.tagName(tagName)
    this
  }

  def prepend(html: String): Elements.this.type = {
    delegate.prepend(html)
    this
  }

  def append(html: String): Elements.this.type = {
    delegate.append(html)
    this
  }

  def before(html: String): Elements.this.type = {
    delegate.before(html)
    this
  }

  def after(html: String): Elements.this.type = {
    delegate.after(html)
    this
  }

  def wrap(html: String): Elements.this.type = {
    delegate.wrap(html)
    this
  }

  def unwrap(): Elements.this.type = {
    delegate.unwrap()
    this
  }

  def empty(): Elements.this.type = {
    delegate.empty()
    this
  }

  def remove(): Elements.this.type = {
    delegate.remove()
    this
  }

  // filters

  def \(that: String): Elements = {
    val elements = Elements()
    this.foreach(elem => elements ++= (elem \ that))
    elements
  }

  def css(query: String): Elements = Elements(delegate.select(query))

  def not(query: String): Elements = Elements(delegate.not(query))

  def eq(index: Int): Elements = Elements(delegate.eq(index))

  def is(query: String): Boolean = delegate.is(query)

  def next: Elements = Elements(delegate.next())

  def next(query: String): Elements = Elements(delegate.next(query))

  def nextAll: Elements = Elements(delegate.nextAll())

  def nextAll(query: String): Elements = Elements(delegate.nextAll(query))

  def prev: Elements = Elements(delegate.prev())

  def prev(query: String): Elements = Elements(delegate.prev(query))

  def prevAll: Elements = Elements(delegate.prevAll())

  def prevAll(query: String): Elements = Elements(delegate.prevAll(query))

  def parents: Elements = Elements(delegate.parents())

  override def head: Element = Element(delegate.first())

  def first: Element = Element(delegate.first())

  override def last: Element = Element(delegate.last())

  def traverse(nodeVisitor: NodeVisitor): Elements.this.type = {
    delegate.traverse(nodeVisitor)
    this
  }

  def filter(nodeFilter: NodeFilter): Elements.this.type = {
    delegate.filter(nodeFilter)
    this
  }

  def forms: mutable.Buffer[FormElement] =
    this.filter(_.isInstanceOf[FormElement]).asInstanceOf[mutable.Buffer[FormElement]]

  // Buffer functions

  override def filter(p: Element => Boolean): Elements = {
    val elements = Elements()
    this.foreach(elem => if (p(elem)) elements += elem)
    elements
  }

  override def apply(n: Int): Element = Element(delegate.get(n))

  override def update(n: Int, newelem: Element): Unit = delegate.set(n, newelem.delegate)

  override def length: Int = delegate.size()

  override def +=(elem: Element): Elements.this.type = {
    delegate.add(elem.delegate)
    this
  }

  def +=(html: String): Elements.this.type = {
    delegate.append(html)
    this
  }

  override def clear(): Unit = delegate.clear()

  override def +=:(elem: Element): Elements.this.type = {
    delegate.add(0, elem.delegate)
    this
  }

  def +=:(html: String): Elements.this.type = {
    delegate.prepend(html)
    this
  }

  override def insertAll(n: Int, elems: Traversable[Element]): Unit =
    delegate.addAll(n, elems.map(_.delegate).toVector.asJavaCollection)

  override def remove(n: Int): Element = Element(delegate.remove(n))


  override def iterator: Iterator[Element] =
    TransformationIterator.fromJava[Element, jn.Element](delegate.iterator(), Element.apply)

  override def clone(): Elements = Elements(delegate.clone())

  override def hashCode(): Int = delegate.hashCode()

  override def equals(that: Any): Boolean =
    that.isInstanceOf[Elements] && that.asInstanceOf[Elements].delegate == delegate

  override def toString(): String = delegate.toString
}

object Elements {
  def apply(elements: org.jsoup.select.Elements): Elements = if (elements != null) new Elements(elements) else null

  def apply(initialCapacity: Int): Elements = Elements(new js.Elements(initialCapacity))

  def apply(elements: Element*): Elements = Elements(new js.Elements(elements.view.map(_.delegate).asJavaCollection))
}