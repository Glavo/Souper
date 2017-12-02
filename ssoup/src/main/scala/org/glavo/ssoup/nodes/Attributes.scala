package org.glavo.ssoup.nodes

import org.jsoup.{nodes => js}

final class Attributes(val asJsoup: js.Attributes) extends Iterable[Attribute] {
  override def iterator: Iterator[Attribute] = new Iterator[Attribute] {
    private val it = asJsoup.iterator()

    override def hasNext: Boolean = it.hasNext

    override def next(): Attribute = Attribute(it.next())
  }
}
