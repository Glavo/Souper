package org.glavo.souper.nodes

import org.glavo.souper.select.{NodeFilter, NodeVisitor}
import org.jetbrains.annotations.{Contract, NotNull, Nullable}
import org.jsoup.{nodes => jn}

import scala.collection.JavaConverters._
import scala.collection.mutable

/** The base, abstract Node model. Elements, Documents, Comments etc are all Node instances. */
abstract class Node extends Cloneable {
  type Self = Node.this.type

  val asJsoup: jn.Node

  /** Get the node name of this node. Use for debugging purposes and not logic switching (for that, use instanceof). */
  @NotNull
  @Contract(pure = true)
  @inline
  final def nodeName: String = asJsoup.nodeName()

  @inline
  final def hasParent: Boolean = asJsoup.hasParent

  /** Get an attribute's value by its key. <b>Case insensitive</b>
    *
    * To get an absolute URL from an attribute that may be a relative URL, prefix the key with `abs`,
    * which is a shortcut to the `absUrl` method.
    *
    * E.g.:
    *
    * {{{
    *     String url = a.attr("abs:href");
    * }}}
    *
    * @param attributeKey The attribute key.
    * @return The attribute, or empty string if not present (to avoid nulls).
    * @see `attributes()`
    * @see `hasAttr(String)`
    * @see `absUrl(String)`
    */
  @NotNull
  @inline
  final def attr(@NotNull attributeKey: String): String = asJsoup.attr(attributeKey)

  /** Get all of the element's attributes. */
  @inline
  final def attributes: Attributes = Attributes(asJsoup.attributes())

  /**
    * Set an attribute (key=value). If the attribute already exists, it is replaced. The attribute key comparison is
    * <b>case insensitive</b>.
    *
    * @param attributeKey   The attribute key.
    * @param attributeValue The attribute value.
    * @return this (for chaining)
    */
  @inline
  final def attr(@NotNull attributeKey: String, attributeValue: String): Node.this.type = {
    asJsoup.attr(attributeKey, attributeValue)
    this
  }

  /** Test if this element has an attribute. <b>Case insensitive</b>
    *
    * @param attributeKey The attribute key to check.
    * @return true if the attribute exists, false if not.
    */
  @inline
  final def hasAttr(@NotNull attributeKey: String): Boolean = asJsoup.hasAttr(attributeKey)

  /** Remove an attribute from this element.
    *
    * @param attributeKey The attribute to remove.
    * @return this (for chaining)
    */
  @inline
  final def removeAttr(@NotNull attributeKey: String): Node.this.type = {
    asJsoup.removeAttr(attributeKey)
    this
  }

  /** Clear (remove) all of the attributes in this node.
    *
    * @return this, for chaining
    */
  @inline
  final def clearAttributes(): Node.this.type = {
    asJsoup.clearAttributes()
    this
  }

  /** Get the base URI of this node. */
  @NotNull
  @inline
  def baseUri: String = asJsoup.baseUri()

  /** Update the base URI of this node and all of its descendants. */
  @inline
  def baseUri_=(@NotNull baseUri: String): Unit = asJsoup.setBaseUri(baseUri)

  /** Get an absolute URL from a URL attribute that may be relative (i.e. an `&lt;a href&gt;` or
    * `&lt;img src&gt;`).
    *
    * E.g.: `String absUrl = linkEl.absUrl("href");`
    *
    * If the attribute value is already absolute (i.e. it starts with a protocol, like
    * `http://` or `https://` etc), and it successfully parses as a URL, the attribute is
    * returned directly. Otherwise, it is treated as a URL relative to the element's `baseUri`, and made
    * absolute using that.
    *
    * As an alternate, you can use the `attr` method with the `abs:` prefix, e.g.:
    * `String absUrl = linkEl.attr("abs:href");`
    *
    * @param attributeKey The attribute key
    * @return An absolute URL if one could be made, or an empty string (not null) if the attribute was missing or
    *         could not be made successfully into a URL.
    * @see #attr
    * @see [[java.net.URL(java.net.URL, String)]]
    */
  @inline
  def absUrl(@NotNull attributeKey: String): String = asJsoup.absUrl(attributeKey)

  /** Get a child node by its 0-based index.
    *
    * @param index index of child node
    * @return the child node at this index. Throws a { @code IndexOutOfBoundsException} if the index is out of bounds.
    */
  @inline
  def childNode(index: Int): Node = Node(asJsoup.childNode(index))

  /** Get this node's children. Presented as an unmodifiable list: new children can not be added, but the child nodes
    * themselves can be manipulated.
    *
    * @throws UnsupportedOperationException if this node is LeafNode
    * @return list of children. If no children, returns an empty list.
    */
  @NotNull
  @Contract(pure = true)
  @inline
  def childNodes: Seq[Node] = new Seq[Node] {
    private val list = asJsoup.childNodes()

    override def length: Int = list.size()

    override def apply(idx: Int) = Node(list.get(idx))

    override def iterator: Iterator[Node] = new Iterator[Node] {
      private val it = list.iterator()

      override def hasNext: Boolean = it.hasNext

      override def next() = Node(it.next())
    }
  }

  /** Returns a deep copy of this node's children. Changes made to
    * these nodes will not be reflected in the original nodes */
  @NotNull
  @Contract(pure = true)
  @inline
  def childNodesCopy: mutable.Buffer[Node] = new Node.ListView(asJsoup.childNodesCopy)

  /** Get the number of child nodes that this node holds. */
  @Contract(pure = true)
  @inline
  def childNodeSize: Int = asJsoup.childNodeSize()

  /** Gets this node's parent node.
    *
    * @return parent node; or null if no parent.
    */
  @Nullable
  @Contract(pure = true)
  @inline
  def parent = Node(asJsoup.parent())

  /** Gets this node's parent node. Not overridable by extending classes, so useful if you really just need the Node type.
    *
    * @return parent node; or null if no parent.
    */
  @Nullable
  @Contract(pure = true)
  @inline
  def parentNode = Node(asJsoup.parentNode())

  /** Get this node's root node; that is, its topmost ancestor. If this node is the top ancestor, returns `this`. */
  @NotNull
  @Contract(pure = true)
  @inline
  def root = Node(asJsoup.root())

  /** Gets the Document associated with this Node.
    *
    * @return the Document associated with this Node, or null if there is no such Document.
    */
  @Nullable
  @Contract(pure = true)
  @inline
  def ownerDocument = Document(asJsoup.ownerDocument())

  /** Remove (delete) this node from the DOM tree. If this node has children, they are also removed. */
  @inline
  def remove(): Unit = asJsoup.remove()


  /** Insert the specified HTML into the DOM before this node (i.e. as a preceding sibling).
    *
    * @param html HTML to add before this node
    * @return this node, for chaining
    * @see `after(String)`
    */
  @inline
  def before(@NotNull html: String): Self = {
    asJsoup.before(html)
    this
  }

  /** Insert the specified node into the DOM before this node (i.e. as a preceding sibling).
    *
    * @param node to add before this node
    * @return this node, for chaining
    * @see `after(Node)`
    */
  @inline
  def before(@NotNull node: Node): Self = {
    asJsoup.before(node.asJsoup)
    this
  }

  /** Insert the specified HTML into the DOM after this node (i.e. as a following sibling).
    *
    * @param html HTML to add after this node
    * @return this node, for chaining
    * @see `before(String)`
    */
  @inline
  def after(@NotNull html: String): Self = {
    asJsoup.after(html)
    this
  }

  /** Insert the specified node into the DOM after this node (i.e. as a following sibling).
    *
    * @param node to add after this node
    * @return this node, for chaining
    * @see `before(Node)`
    */
  @inline
  def after(@NotNull node: Node): Self = {
    asJsoup.after(node.asJsoup)
    this
  }

  /** Wrap the supplied HTML around this node.
    *
    * @param html HTML to wrap around this element, e.g. `<div class="head"></div>`. Can be arbitrarily deep.
    * @return this node, for chaining.
    */
  @Nullable
  @inline
  def wrap(@NotNull html: String): Self = {
    if (asJsoup.wrap(html) != null) this else null
  }

  /** Removes this node from the DOM, and moves its children up into the node's parent. This has the effect of dropping
    * the node but keeping its children.
    *
    * For example, with the input html:
    *
    * `<div>One <span>Two <b>Three</b></span></div>`
    * Calling `element.unwrap()` on the `span` element will result in the html:
    * `<div>One Two <b>Three</b></div>`
    * and the `"Two "` [[org.glavo.souper.nodes.TextNode]] being returned.
    *
    * @return the first child of this node, after the node has been unwrapped. Null if the node had no children.
    * @see `remove()`
    * @see `wrap(String)`
    */
  @Nullable
  @Contract(pure = true)
  @inline
  def unwrap: Node = Node(asJsoup.unwrap())

  /** Replace this node in the DOM with the supplied node.
    *
    * @param in the node that will will replace the existing node.
    */
  @inline
  def replaceWith(@NotNull in: Node): Unit = asJsoup.replaceWith(in.asJsoup)

  /** Retrieves this node's sibling nodes. Similar to `childNodes()  node.parent.childNodes()`, but does not
    * include this node (a node is not a sibling of itself).
    *
    * @return node siblings. If the node has no parent, returns an empty list.
    */
  @NotNull
  @Contract(pure = true)
  @inline
  def siblingNodes: mutable.Buffer[Node] = new Node.ListView(asJsoup.siblingNodes())

  /** Get this node's next sibling.
    *
    * @return next sibling, or null if this is the last sibling
    */
  @Nullable
  @Contract(pure = true)
  @inline
  def nextSibling: Node = Node(asJsoup.nextSibling())

  /** Get this node's previous sibling.
    *
    * @return the previous sibling, or null if this is the first sibling
    */
  @Nullable
  @inline
  def previousSibling: Node = Node(asJsoup.previousSibling())

  /** Get the list index of this node in its node sibling list. I.e. if this is the first node
    * sibling, returns 0.
    *
    * @return position in node sibling list
    * @see [[org.glavo.souper.nodes.Element#elementSiblingIndex()]]
    */
  @Contract(pure = true)
  @inline
  def siblingIndex: Int = asJsoup.siblingIndex()

  /** Perform a depth-first traversal through this node and its descendants.
    *
    * @param nodeVisitor the visitor callbacks to perform on each node
    * @return this node, for chaining
    */
  @inline
  def traverse(@NotNull nodeVisitor: NodeVisitor): Self = {
    asJsoup.traverse(nodeVisitor)
    this
  }

  /** Perform a depth-first filtering through this node and its descendants.
    *
    * @param nodeFilter the filter callbacks to perform on each node
    * @return this node, for chaining
    */
  @inline
  def filter(@NotNull nodeFilter: NodeFilter): Self = {
    asJsoup.filter(nodeFilter)
    this
  }

  /** Get the outer HTML of this node. */
  @NotNull
  @Contract(pure = true)
  @inline
  def outerHtml: String = asJsoup.outerHtml()

  /** Write this node and its children to the given [[java.lang.Appendable]].
    *
    * @param appendable the [[java.lang.Appendable]] to write to.
    * @return the supplied [[java.lang.Appendable]], for chaining.
    */
  @inline
  def html[A <: Appendable](@NotNull appendable: A): appendable.type = asJsoup.html(appendable)

  override def clone() = Node(asJsoup.clone())

  override def toString: String = asJsoup.toString

  final override def equals(obj: scala.Any): Boolean = obj.isInstanceOf[Node] && obj.asInstanceOf[Node].asJsoup == asJsoup

  final override def hashCode(): Int = asJsoup.hashCode()
}

object Node {
  @Nullable
  @Contract(value = "null -> null; !null -> !null", pure = true)
  def apply(@Nullable node: jn.Node): Node = node match {
    case null => null
    case elem: jn.Element => Element(elem)

    //LeafNode
    case comment: jn.Comment => Comment(comment)
    case data: jn.DataNode => DataNode(data)
    case docType: jn.DocumentType => DocumentType(docType)
    case text: jn.TextNode => TextNode(text)
    case xml: jn.XmlDeclaration => XmlDeclaration(xml)
    case _ => new Node {
      override val asJsoup: jn.Node = node
    }
  }

  private class ListView(val list: java.util.List[jn.Node]) extends mutable.Buffer[Node] {
    override def apply(n: Int): Node = Node(list.get(n))

    override def update(n: Int, newelem: Node): Unit = list.set(n, newelem.asJsoup)

    override def length: Int = list.size()

    override def +=(elem: Node): ListView.this.type = {
      list.add(elem.asJsoup)
      this
    }

    override def clear(): Unit = list.clear()

    override def +=:(elem: Node): ListView.this.type = {
      if (list.size() == 0) list.add(elem.asJsoup)
      else list.add(0, elem.asJsoup)
      this
    }

    override def insertAll(n: Int, elems: Traversable[Node]): Unit = list.addAll(n, elems.map(_.asJsoup).toSeq.asJavaCollection)

    override def remove(n: Int): Node = Node(list.remove(n))

    override def iterator: Iterator[Node] = new Iterator[Node] {
      private val it = list.iterator()

      override def hasNext: Boolean = it.hasNext

      override def next(): Node = Node(it.next())
    }
  }

}