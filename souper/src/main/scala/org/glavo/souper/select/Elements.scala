package org.glavo.souper.select

import org.glavo.souper.nodes.Element
import org.jsoup.{select => js}

import scala.collection.JavaConverters._
import scala.collection.mutable

class Elements private(val asJsoup: js.Elements) extends mutable.Buffer[Element] {
  override def apply(n: Int): Element = Element(asJsoup.get(n))

  override def update(n: Int, newelem: Element): Unit = asJsoup.set(n, newelem.asJsoup)

  override def length: Int = asJsoup.size()

  override def +=(elem: Element): Elements.this.type = {
    asJsoup.add(elem.asJsoup)
    this
  }

  override def clear(): Unit = asJsoup.clear()

  override def +=:(elem: Element): Elements.this.type = {
    asJsoup.add(0, elem.asJsoup)
    this
  }

  //noinspection NotImplementedCode
  override def insertAll(n: Int, elems: Traversable[Element]): Unit =
    asJsoup.addAll(n , elems.map(_.asJsoup).toVector.asJavaCollection)

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
}