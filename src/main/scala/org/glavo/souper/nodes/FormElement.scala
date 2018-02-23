package org.glavo.souper.nodes

import org.glavo.souper._
import org.glavo.souper.select.Elements
import org.jsoup.{nodes => jn}

import scala.collection.JavaConverters._
import scala.collection.mutable

final class FormElement(override val delegate: jn.FormElement) extends Element(delegate) {
  def elements: Elements = Elements(delegate.elements())

  def addElement(element: Element): FormElement.this.type = {
    delegate.addElement(element.delegate)
    this
  }

  def submit: Connection = delegate.submit().asSouper

  def formData: mutable.Buffer[Connection.KeyVal] = new mutable.Buffer[Connection.KeyVal] {
    private val data = delegate.formData()

    override def apply(n: Int): Connection.KeyVal = data.get(n).asSouper

    override def update(n: Int, newelem: Connection.KeyVal): Unit = data.set(n, newelem.asJsoup)

    override def length: Int = data.size()

    override def +=(elem: Connection.KeyVal): this.type = {
      data.add(elem.asJsoup)
      this
    }

    override def clear(): Unit = data.clear()

    override def +=:(elem: Connection.KeyVal): this.type = {
      data.add(0, elem.asJsoup)
      this
    }

    override def insertAll(n: Int, elems: Traversable[Connection.KeyVal]): Unit = {
      data.addAll(n, elems.map(_.asJsoup).toSeq.asJava)
    }

    override def remove(n: Int): Connection.KeyVal = data.remove(n).asSouper

    override def iterator: Iterator[Connection.KeyVal] = new Iterator[Connection.KeyVal] {
      private val it = data.iterator()

      override def hasNext: Boolean = it.hasNext

      override def next(): Connection.KeyVal = it.next().asSouper
    }
  }
}

object FormElement {
  def apply(asJsoup: jn.FormElement): FormElement = if (asJsoup == null) null else new FormElement(asJsoup)
}