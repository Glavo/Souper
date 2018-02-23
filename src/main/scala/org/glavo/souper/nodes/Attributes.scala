package org.glavo.souper.nodes

import org.jetbrains.annotations.{Contract, NotNull}
import org.jsoup.{nodes => jn}

import scala.collection.JavaConverters._
import scala.collection.mutable

/** The attributes of an Element.
  *
  * Attributes are treated as a map: there can be only
  * one value associated with an attribute key/name.
  *
  * Attribute name and value comparisons are  generally
  * <b>case sensitive</b>. By default for HTML, attribute names are
  * normalized to lower-case on parsing. That means you should use
  * lower-case strings when referring to attributes by name.
  */
final class Attributes(val delegate: jn.Attributes) extends Iterable[Attribute] {

  @NotNull
  @Contract(pure = true)
  @inline
  def apply(@NotNull key: String): String = delegate.get(key)

  /** Get an attribute value by key.
    *
    * @param key the (case-sensitive) attribute key
    * @return the attribute value if set; or empty string if not set (or a boolean attribute).
    * @see [[org.glavo.souper.nodes.Attribute]], method `hasKey`.
    */
  @NotNull
  @Contract(pure = true)
  @inline
  def get(@NotNull key: String): String = delegate.get(key)

  /** Get an attribute's value by case-insensitive key
    *
    * @param key the attribute name
    * @return the first matching attribute value if set; or empty string if not set (ora boolean attribute).
    */
  @NotNull
  @Contract(pure = true)
  @inline
  def getIgnoreCase(@NotNull key: String): String = delegate.getIgnoreCase(key)

  @inline
  def +=(@NotNull key: String, value: String): Attributes.this.type = {
    delegate.put(key, value)
    this
  }

  /** Set a new attribute, or replace an existing one by key.
    *
    * @param key   case sensitive attribute key
    * @param value attribute value
    * @return these attributes, for chaining
    */
  @inline
  def put(@NotNull key: String, value: String): Attributes.this.type = {
    delegate.put(key, value)
    this
  }

  @inline
  def +=(key: String, value: Boolean): Attributes.this.type = {
    delegate.put(key, value)
    this
  }

  /** Set a new boolean attribute, remove attribute if value is false.
    *
    * @param key   case <b>insensitive</b> attribute key
    * @param value attribute value
    * @return these attributes, for chaining
    */
  @inline
  def put(@NotNull key: String, value: Boolean): Attributes.this.type = {
    delegate.put(key, value)
    this
  }

  @inline
  def +=(@NotNull attribute: Attribute): Attributes.this.type = {
    delegate.put(attribute.delegate)
    this
  }

  /** Set a new attribute, or replace an existing one by key.
    *
    * @param attribute attribute with case sensitive key
    * @return these attributes, for chaining
    */
  @inline
  def put(@NotNull attribute: Attribute): Attributes.this.type = {
    delegate.put(attribute.delegate)
    this
  }

  @inline
  def -=(@NotNull key: String): Unit = delegate.remove(key)

  /** Remove an attribute by key. <b>Case sensitive.</b>
    *
    * @param key attribute key to remove
    */
  @inline
  def remove(@NotNull key: String): Unit = delegate.remove(key)

  /** Remove an attribute by key. <b>Case insensitive.</b>
    *
    * @param key attribute key to remove
    */
  @inline
  def removeIgnoreCase(@NotNull key: String): Unit = delegate.removeIgnoreCase(key)

  /** Tests if these attributes contain an attribute with this key.
    *
    * @param key case-sensitive key to check for
    * @return true if key exists, false otherwise
    */
  @Contract(pure = true)
  @inline
  def hasKey(@NotNull key: String): Boolean = delegate.hasKey(key)

  /** Tests if these attributes contain an attribute with this key.
    *
    * @param key key to check for
    * @return true if key exists, false otherwise
    */
  @Contract(pure = true)
  @inline
  def hasKeyIgnoreCase(@NotNull key: String): Boolean = delegate.hasKeyIgnoreCase(key)

  /** Get the number of attributes in this set.
    *
    * @return size
    */
  override def size: Int = delegate.size()


  @inline
  def ++=(@NotNull incoming: Attributes): Unit = delegate.addAll(incoming.delegate)

  /** Add all the attributes from the incoming set to this set.
    *
    * @param incoming attributes to add to these attributes.
    */
  @inline
  def addAll(@NotNull incoming: Attributes): Unit = delegate.addAll(incoming.delegate)

  /** Retrieves a filtered view of attributes that are HTML5 custom data attributes; that is, attributes with keys
    * starting with `data-`.
    *
    * @return map of custom data attributes.
    */
  @NotNull
  @Contract(pure = true)
  @inline
  def dataset: mutable.Map[String, String] = delegate.dataset().asScala

  /**
    * Get the HTML representation of these attributes.
    *
    * @return HTML
    * @throws org.glavo.souper.SerializationException if the HTML representation of the attributes cannot be constructed.
    */
  @Contract(pure = true)
  @inline
  def html: String = delegate.html()

  /** Internal method. Lowercases all keys.*/
  @inline
  def normalize(): Unit = delegate.normalize()

  override def iterator: Iterator[Attribute] = new Iterator[Attribute] {
    private val it = delegate.iterator()

    override def hasNext: Boolean = it.hasNext

    override def next() = new Attribute(it.next())
  }

  override def toString(): String = delegate.toString

  override def equals(obj: scala.Any): Boolean =
    obj.isInstanceOf[Attributes] && obj.asInstanceOf[Attributes].delegate == delegate

  override def hashCode(): Int = delegate.hashCode()
}

object Attributes {
  @inline
  def apply(asJsoup: jn.Attributes): Attributes = if (asJsoup == null) null else new Attributes(asJsoup)

  @inline
  def apply(): Attributes = new Attributes(new jn.Attributes())

  @inline
  def unapplySeq(attributes: Attributes): Option[Seq[Attribute]] = Some(attributes.toSeq)

  @inline
  def unapplySeq(attributes: jn.Attributes): Option[Seq[Attribute]] = unapplySeq(new Attributes(attributes))
}
