package org.glavo.souper.parser

import org.jsoup.{parser => jp}

import scala.collection.mutable
import scala.collection.JavaConverters._

class ParseErrorList private(val asJsoup: jp.ParseErrorList) extends mutable.Buffer[ParseError] {
  override def apply(n: Int): ParseError = ParseError(asJsoup.get(n))

  override def update(n: Int, newelem: ParseError): Unit = asJsoup.set(n, newelem.asJsoup)

  override def length: Int = asJsoup.size()

  override def +=(elem: ParseError): ParseErrorList.this.type = {
    asJsoup.add(elem.asJsoup)
    this
  }

  override def clear(): Unit = asJsoup.clear()

  override def +=:(elem: ParseError): ParseErrorList.this.type = {
    asJsoup.add(0, elem.asJsoup)
    this
  }

  override def insertAll(n: Int, elems: Traversable[ParseError]): Unit = {
    asJsoup.addAll(n, elems.map(_.asJsoup).toSeq.asJavaCollection)
  }

  override def remove(n: Int): ParseError = ParseError(asJsoup.remove(n))

  override def iterator: Iterator[ParseError] = new Iterator[ParseError] {
    private val it = asJsoup.iterator()

    override def hasNext: Boolean = it.hasNext

    override def next(): ParseError = ParseError(it.next())
  }

  override def toString(): String = asJsoup.toString
}

object ParseErrorList {
  @inline
  def apply(errorList: jp.ParseErrorList): ParseErrorList =
    if(errorList == null) null else new ParseErrorList(errorList)
}