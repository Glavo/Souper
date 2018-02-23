package org.glavo.souper.parser

import org.jsoup.{parser => jp}

import scala.collection.mutable
import scala.collection.JavaConverters._

class ParseErrorList(val delegate: jp.ParseErrorList) extends mutable.Buffer[ParseError] {
  override def apply(n: Int): ParseError = ParseError(delegate.get(n))

  override def update(n: Int, newelem: ParseError): Unit = delegate.set(n, newelem.delegate)

  override def length: Int = delegate.size()

  override def +=(elem: ParseError): ParseErrorList.this.type = {
    delegate.add(elem.delegate)
    this
  }

  override def clear(): Unit = delegate.clear()

  override def +=:(elem: ParseError): ParseErrorList.this.type = {
    delegate.add(0, elem.delegate)
    this
  }

  override def insertAll(n: Int, elems: Traversable[ParseError]): Unit = {
    delegate.addAll(n, elems.map(_.delegate).toSeq.asJavaCollection)
  }

  override def remove(n: Int): ParseError = ParseError(delegate.remove(n))

  override def iterator: Iterator[ParseError] = new Iterator[ParseError] {
    private val it = delegate.iterator()

    override def hasNext: Boolean = it.hasNext

    override def next(): ParseError = ParseError(it.next())
  }

  override def toString(): String = delegate.toString
}

object ParseErrorList {
  @inline
  def apply(errorList: jp.ParseErrorList): ParseErrorList =
    if (errorList == null) null else new ParseErrorList(errorList)
}