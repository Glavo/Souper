package org.glavo.souper.nodes

import org.jetbrains.annotations.{NotNull, Nullable, Contract}
import org.jsoup.{nodes => jn}

/** A single key + value attribute. (Only used for presentation.)
  * @constructor  Create a attribute from jsoup attribute.
  */
final class Attribute(val delegate: jn.Attribute) extends Product2[String, String] with Cloneable {
  /** Return the attribute key. */
  @NotNull
  @Contract(pure = true)
  def key: String = delegate.getKey

  /** Set the attribute key; case is preserved.
    *
    * @param key the new key
    */
  def key_=(@NotNull key: String): Unit = delegate.setKey(key)

  /** Return the attribute value. */
  @Contract(pure = true)
  def value: String = delegate.getValue

  /** Set the attribute value.
    *
    * @param value the new attribute value; must not be null
    */
  def value_=(value: String): Unit = delegate.setValue(value)

  /** Return the HTML representation of this attribute; e.g. `href="index.html"`. */
  @NotNull
  def html: String = delegate.html()

  override def _1: String = delegate.getKey

  override def _2: String = delegate.getValue

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Attribute]

  override def clone() = new Attribute(delegate.clone())

  override def hashCode(): Int = delegate.hashCode()

  override def equals(obj: scala.Any): Boolean = canEqual(obj) && obj.asInstanceOf[Attribute].delegate == delegate

  override def toString: String = delegate.toString
}

object Attribute {
  def apply(attribute: jn.Attribute): Attribute = new Attribute(attribute)

  /** Create a new attribute from unencoded (raw) key and value.
    *
    * @param key    attribute key; case is preserved.
    * @param value  attribute value
    * @param parent the containing Attributes (this Attribute is not automatically added to said Attributes)
    * @see [[org.glavo.souper.nodes.Attribute#createFromEncoded]]
    */
  def apply(@NotNull key: String, value: String, @Nullable parent: Attributes = null) =
    new Attribute(new jn.Attribute(key, value, if (parent == null) null else parent.delegate))

  def unapply(arg: Attribute): Option[(String, String)] = Some((arg.key, arg.value))

  def unapply(arg: jn.Attribute): Option[(String, String)] = Some((arg.getKey, arg.getValue))

  @inline
  def createFromEncoded(unencodedKey: String, encodedValue: String): Attribute =
    new Attribute(jn.Attribute.createFromEncoded(unencodedKey, encodedValue))

}