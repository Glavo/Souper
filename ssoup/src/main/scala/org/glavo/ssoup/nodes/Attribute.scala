package org.glavo.ssoup.nodes

import org.jsoup.{nodes => js}

final class Attribute(val asJsoup: js.Attribute) extends Product2[String, String] with Cloneable {
  def key: String = asJsoup.getKey

  def key_=(key: String): Unit = asJsoup.setKey(key)

  def value: String = asJsoup.getValue

  def value_=(value: String): Unit = asJsoup.setValue(value)

  def html: String = asJsoup.html()

  override def _1: String = asJsoup.getKey

  override def _2: String = asJsoup.getValue

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Attribute]

  override def clone(): AnyRef = new Attribute(asJsoup.clone())

  override def hashCode(): Int = asJsoup.hashCode()

  override def equals(obj: scala.Any): Boolean = canEqual(obj) && obj.asInstanceOf[Attribute].asJsoup == asJsoup

  override def toString: String = asJsoup.toString
}

object Attribute {

  def apply(asJsoup: js.Attribute): Attribute = new Attribute(asJsoup)

  def unapply(arg: Attribute): Option[(String, String)] = Some((arg.key, arg.value))

  def unapply(arg: js.Attribute): Option[(String, String)] = Some((arg.getKey, arg.getValue))

  @inline
  def createFromEncoded(unencodedKey: String, encodedValue: String): Attribute =
    new Attribute(js.Attribute.createFromEncoded(unencodedKey, encodedValue))

}