package org.glavo.souper.nodes

import org.jsoup.{nodes => js}

import scala.collection.JavaConverters._
import scala.collection.mutable

final class Attributes private(val asJsoup: js.Attributes) extends Iterable[Attribute] {

  def apply(key: String): String = asJsoup.get(key)

  def get(key: String): String = asJsoup.get(key)

  def getIgnoreCase(key: String): String = asJsoup.getIgnoreCase(key)

  def +=(key: String, value: String): Attributes.this.type = {
    asJsoup.put(key, value)
    this
  }

  def put(key: String, value: String): Attributes.this.type = {
    asJsoup.put(key, value)
    this
  }

  def +=(key: String, value: Boolean): Attributes.this.type = {
    asJsoup.put(key, value)
    this
  }

  def put(key: String, value: Boolean): Attributes.this.type = {
    asJsoup.put(key, value)
    this
  }

  def +=(attribute: Attribute): Attributes.this.type = {
    asJsoup.put(attribute.asJsoup)
    this
  }

  def put(attribute: Attribute): Attributes.this.type = {
    asJsoup.put(attribute.asJsoup)
    this
  }

  def -=(key: String): Unit = asJsoup.remove(key)

  def remove(key: String): Unit = asJsoup.remove(key)

  def removeIgnoreCase(key: String): Unit = asJsoup.removeIgnoreCase(key)

  def hasKey(key: String): Boolean = asJsoup.hasKey(key)

  def hasKeyIgnoreCase(key: String): Boolean = asJsoup.hasKeyIgnoreCase(key)

  def ++=(incoming: Attributes): Unit = asJsoup.addAll(incoming.asJsoup)

  def addAll(incoming: Attributes): Unit = asJsoup.addAll(incoming.asJsoup)

  def dataset: mutable.Map[String, String] = asJsoup.dataset().asScala

  def html: String = asJsoup.html()

  def normalize(): Unit = asJsoup.normalize()

  override def iterator: Iterator[Attribute] = new Iterator[Attribute] {
    private val it = asJsoup.iterator()

    override def hasNext: Boolean = it.hasNext

    override def next(): Attribute = Attribute(it.next())
  }

  override def toString(): String = asJsoup.toString

  override def equals(obj: scala.Any): Boolean =
    obj.isInstanceOf[Attributes] && obj.asInstanceOf[Attributes].asJsoup == asJsoup

  override def hashCode(): Int = asJsoup.hashCode()
}

object Attributes {
  def apply(asJsoup: js.Attributes): Attributes = new Attributes(asJsoup)

  def apply(): Attributes = Attributes(new js.Attributes())

  def unapplySeq(attributes: Attributes): Option[Seq[Attribute]] = Some(attributes.toSeq)

  def unapplySeq(attributes: js.Attributes): Option[Seq[Attribute]] = unapplySeq(Attributes(attributes))
}
