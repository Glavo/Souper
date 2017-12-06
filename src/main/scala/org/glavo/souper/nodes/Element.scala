package org.glavo.souper.nodes

import java.util
import java.util.regex.Pattern

import org.glavo.souper.parser.Tag
import org.glavo.souper.select.Selector.SelectorParseException
import org.glavo.souper.select.{Elements, Evaluator, Selector}
import org.jetbrains.annotations.{Contract, NotNull}
import org.jsoup.{nodes => jn}

import scala.collection.{immutable, mutable}
import scala.collection.JavaConverters._
import scala.util.matching.Regex

class Element protected(override val asJsoup: jn.Element) extends Node {
  /** Get the name of the tag for this element. E.g. `div`. */
  @NotNull
  @Contract(pure = true)
  @inline
  def tagName: String = asJsoup.tagName()

  /** Set the tag of this element.
    *
    * @param tagName new tag name for this element
    */
  @inline
  def tagName_=(@NotNull tagName: String): Unit = asJsoup.tagName(tagName)

  /** Change the tag of this element. For example, convert a `<span>` to a `<div>` with
    * `el.tagName("div");`.
    *
    * @param tagName new tag name for this element
    * @return this element, for chaining
    */
  @inline
  def tagName(@NotNull tagName: String): Self = {
    asJsoup.tagName(tagName)
    this
  }

  /** Get the Tag for this element. */
  @NotNull
  @Contract(pure = true)
  @inline
  def tag = Tag(asJsoup.tag)

  /** Test if this element is a block-level element. (E.g. `<div> == true` or an inline element
    * `<p> == false`).
    *
    * @return true if block, false if not (and thus inline)
    */
  @Contract(pure = true)
  @inline
  def isBlock: Boolean = asJsoup.isBlock

  /** Get the `id` attribute of this element.
    *
    * @return The id attribute, if present, or an empty string if not.
    */
  @NotNull
  @Contract(pure = true)
  @inline
  def id: String = asJsoup.id()

  /** Set a boolean attribute value on this element. Setting to `true` sets the attribute value to "" and
    * marks the attribute as boolean so no value is written out. Setting to `false` removes the attribute
    * with the same key if it exists.
    *
    * @param attributeKey   the attribute key
    * @param attributeValue the attribute value
    * @return this element
    */
  @inline
  def attr(@NotNull attributeKey: String, attributeValue: Boolean): Self = {
    asJsoup.attr(attributeKey, attributeValue)
    this
  }

  /** Get this element's HTML5 custom data attributes. Each attribute in the element that has a key
    * starting with "data-" is included the dataset.
    *
    * E.g., the element `<div data-package="jsoup" data-language="Java" class="group">...` has the dataset
    * `package=jsoup, language=java`.
    *
    * This map is a filtered view of the element's attribute map. Changes to one map (add, remove, update) are reflected
    * in the other map.
    *
    * You can find elements that have data attributes using the `[^data-]` attribute key prefix selector.
    *
    * @return a map of `key=value` custom data attributes.
    */
  @NotNull
  @Contract(pure = true)
  @inline
  def dataset: mutable.Map[String, String] = Attributes(asJsoup.attributes()).dataset

  override final def parent: Element = Element(asJsoup.parent())

  /** Get this element's parent and ancestors, up to the document root.
    *
    * @return this element's stack of parents, closest first.
    */
  @NotNull
  @inline
  def parents = Elements(asJsoup.parents())

  /** Get a child element of this element, by its 0-based index number.
    *
    * Note that an element can have both mixed Nodes and Elements as children. This method inspects
    * a filtered list of children that are elements, and the index is based on that filtered list.
    *
    * @param index the index number of the element to retrieve
    * @return the child element, if it exists, otherwise throws an `IndexOutOfBoundsException`
    * @see `childNode(int)`
    */
  @inline
  def child(index: Int) = Element(asJsoup.child(index))

  /** Get this element's child elements.
    *
    * This is effectively a filter on `childNodes()` to get Element nodes.
    *
    * @return child elements. If this element has no children, returns an empty list.
    * @see `childNodes()`
    */
  @NotNull
  @Contract(pure = true)
  @inline
  def children = Elements(asJsoup.children())


  /**
    * Get this element's child text nodes. The list is unmodifiable but the text nodes may be manipulated.
    *
    * This is effectively a filter on `childNodes()` to get Text nodes.
    *
    * @return child text nodes. If this element has no text nodes, returns an
    *         empty list.
    *
    *         For example, with the input HTML: <p>One <span>Two</span> Three <br> Four</p> with the `p` element selected:
    *         <ul>
    *         <li>`p.text()` = `"One Two Three Four"`</li>
    *         <li>`p.ownText()` = `"One Three Four"`</li>
    *         <li>`p.children()` = `Elements[<span>, <br>]`</li>
    *         <li>`p.childNodes()` = `List<Node>["One ", <span>, " Three ", <br>, " Four"]`</li>
    *         <li>`p.textNodes()} = { @code List<TextNode>["One ", " Three ", " Four"]`</li>
    *         </ul>
    */
  @NotNull
  @Contract(pure = true)
  @inline
  def textNodes: scala.collection.immutable.Seq[TextNode] = new immutable.Seq[TextNode] {
    private val list = asJsoup.textNodes()

    override def apply(idx: Int) = TextNode(list.get(idx))

    override def length: Int = list.size()

    override def iterator: Iterator[TextNode] = new Iterator[TextNode] {
      private val it = list.iterator()

      override def hasNext: Boolean = it.hasNext

      override def next() = TextNode(it.next())
    }
  }

  /** Get this element's child data nodes. The list is unmodifiable but the data nodes may be manipulated.
    *
    * This is effectively a filter on `childNodes()` to get Data nodes.
    *
    * @return child data nodes. If this element has no data nodes, returns an
    *         empty list.
    * @see `data()`
    */
  @NotNull
  @Contract(pure = true)
  @inline
  def dataNodes: scala.collection.immutable.Seq[DataNode] = new immutable.Seq[DataNode] {
    private val list = asJsoup.dataNodes()

    override def apply(idx: Int) = DataNode(list.get(idx))

    override def length: Int = list.size()

    override def iterator: Iterator[DataNode] = new Iterator[DataNode] {
      private val it = list.iterator()

      override def hasNext: Boolean = it.hasNext

      override def next() = DataNode(it.next())
    }
  }

  /** Find elements matching selector.
    *
    * @param query selector
    * @return matching elements, empty if none
    * @throws SelectorParseException (unchecked) on an invalid query.
    */
  @NotNull
  @Contract(pure = true)
  @inline
  @throws[SelectorParseException]
  def select(@NotNull query: String)(implicit @NotNull selector: Selector): Elements = selector.select(query, this)

  def \(that: String): Elements = that match {
    case "*" => allElements
    case _ => allElements \ that
  }

  def \@(attributeName: String): String = this.attr(attributeName)

  def css(cssQuery: String): Elements = Elements(asJsoup.select(cssQuery))

  def is(cssQuery: String): Boolean = asJsoup.is(cssQuery)

  def is(evaluator: Evaluator): Boolean = asJsoup.is(evaluator.asJsoup)

  def appendChild(child: Node): Self = {
    asJsoup.appendChild(child.asJsoup)
    this
  }

  def appendTo(parent: Element): Self = {
    asJsoup.appendTo(parent.asJsoup)
    this
  }

  def prependChild(child: Node): Self = {
    asJsoup.prependChild(child.asJsoup)
    this
  }

  def insertChildren(index: Int, children: Iterable[Node]): Self = {
    asJsoup.insertChildren(index, new util.AbstractCollection[jn.Node] {
      override def iterator(): util.Iterator[jn.Node] = new util.Iterator[jn.Node] {
        private val it = children.iterator

        override def next(): jn.Node = it.next().asJsoup

        override def hasNext: Boolean = it.hasNext
      }

      override lazy val size: Int = {
        children.size
      }
    })
    this
  }

  def insertChildren(index: Int, children: Node*): Self =
    insertChildren(index, children)

  def appendElement(tagName: String): Element = Element(asJsoup.appendElement(tagName))

  def prependElement(tagName: String): Element = Element(asJsoup.prependElement(tagName))

  def appendText(text: String): Self = {
    asJsoup.appendText(text)
    this
  }

  def prependText(text: String): Self = {
    asJsoup.appendText(text)
    this
  }

  def +=(html: String): Self = {
    asJsoup.append(html)
    this
  }

  def append(html: String): Self = {
    asJsoup.append(html)
    this
  }

  def +=:(html: String): Self = {
    asJsoup.prepend(html)
    this
  }

  def prepend(html: String): Self = {
    asJsoup.prepend(html)
    this
  }

  def empty(): Self = {
    asJsoup.empty()
    this
  }

  def cssSelector: String = asJsoup.cssSelector()

  def siblingElements: Elements = Elements(asJsoup.siblingElements())

  def nextElementSibling: Element = Element(asJsoup.nextElementSibling())

  def previousElementSibling: Element = Element(asJsoup.previousElementSibling())

  def firstElementSibling: Element = Element(asJsoup.firstElementSibling())

  def elementSiblingIndex: Int = asJsoup.elementSiblingIndex()

  def lastElementSibling: Element = Element(asJsoup.lastElementSibling())

  // DOM type methods

  def getElementsByTag(tagName: String): Elements = Elements(asJsoup.getElementsByTag(tagName))

  def getElementById(id: String): Element = Element(asJsoup.getElementById(id))

  def getElementsByClass(className: String): Elements = Elements(asJsoup.getElementsByClass(className))

  def getElementsByAttribute(key: String): Elements = Elements(asJsoup.getElementsByAttribute(key))

  def getElementsByAttributeStarting(keyPrefix: String): Elements =
    Elements(asJsoup.getElementsByAttributeStarting(keyPrefix))

  def getElementsByAttributeValue(key: String, value: String): Elements =
    Elements(asJsoup.getElementsByAttributeValue(key, value))

  def getElementsByAttributeValueNot(key: String, value: String): Elements =
    Elements(asJsoup.getElementsByAttributeValueNot(key, value))

  def getElementsByAttributeValueStarting(key: String, valuePrefix: String) =
    Elements(asJsoup.getElementsByAttributeValueStarting(key, valuePrefix))

  def getElementsByAttributeValueEnding(key: String, valueSuffix: String) =
    Elements(asJsoup.getElementsByAttributeValueEnding(key, valueSuffix))

  def getElementsByAttributeValueContaining(key: String, matcher: String): Elements =
    Elements(asJsoup.getElementsByAttributeValueContaining(key, matcher))

  def getElementsByAttributeValueMatching(key: String, regex: Regex): Elements =
    Elements(asJsoup.getElementsByAttributeValueMatching(key, regex.pattern))

  def getElementsByAttributeValueMatching(key: String, pattern: Pattern): Elements =
    Elements(asJsoup.getElementsByAttributeValueMatching(key, pattern))

  def getElementsByAttributeValueMatching(key: String, regex: String): Elements =
    Elements(asJsoup.getElementsByAttributeValueMatching(key, regex))

  def getElementsByIndexLessThan(index: Int): Elements =
    Elements(asJsoup.getElementsByIndexLessThan(index))

  def getElementsByIndexGreaterThan(index: Int): Elements =
    Elements(asJsoup.getElementsByIndexGreaterThan(index))

  def getElementsByIndexEquals(index: Int): Elements =
    Elements(asJsoup.getElementsByIndexEquals(index))

  def getElementsContainingText(searchText: String): Elements =
    Elements(asJsoup.getElementsContainingText(searchText))

  def getElementsContainingOwnText(searchText: String): Elements =
    Elements(asJsoup.getElementsContainingOwnText(searchText))

  def getElementsMatchingText(regex: Regex): Elements =
    Elements(asJsoup.getElementsMatchingText(regex.pattern))

  def getElementsMatchingText(pattern: Pattern): Elements =
    Elements(asJsoup.getElementsMatchingText(pattern))

  def getElementsMatchingText(regex: String): Elements =
    Elements(asJsoup.getElementsMatchingText(regex))

  def getElementsMatchingOwnText(regex: Regex): Elements =
    Elements(asJsoup.getElementsMatchingOwnText(regex.pattern))

  def getElementsMatchingOwnText(pattern: Pattern): Elements =
    Elements(asJsoup.getElementsMatchingOwnText(pattern))

  def getElementsMatchingOwnText(regex: String): Elements =
    Elements(asJsoup.getElementsMatchingOwnText(regex))

  def allElements: Elements = Elements(asJsoup.getAllElements)

  def getAllElements: Elements = Elements(asJsoup.getAllElements)

  def text: String = asJsoup.text()

  def text_=(text: String): Unit = asJsoup.text(text)

  def text(text: String): Self = {
    asJsoup.text(text)
    this
  }

  def wholeText: String = asJsoup.wholeText()

  def ownText: String = asJsoup.ownText()

  def hasText: Boolean = asJsoup.hasText

  def data: String = asJsoup.data()

  def className: String = asJsoup.className()

  def classNames: mutable.Set[String] = asJsoup.classNames().asScala

  def classNames_=(classNames: mutable.Set[String]): Unit = asJsoup.classNames(classNames.asJava)

  def classNames(classNames: mutable.Set[String]): Self = {
    asJsoup.classNames(classNames.asJava)
    this
  }

  def hasClass(className: String): Boolean = asJsoup.hasClass(className)

  def addClass(className: String): Self = {
    asJsoup.addClass(className)
    this
  }

  def removeClass(className: String): Self = {
    asJsoup.removeClass(className)
    this
  }

  def toggleClass(className: String): Self = {
    asJsoup.toggleClass(className)
    this
  }

  def value: String = asJsoup.`val`()

  def value_=(value: String): Unit = asJsoup.`val`(value)

  def value(value: String): Self = {
    asJsoup.`val`(value)
    this
  }

  def html: String = asJsoup.html()

  def html(html: String): Self = {
    asJsoup.html(html)
    this
  }
}

object Element {
  def apply(elem: jn.Element): Element = elem match {
    case null => null
    case doc: jn.Document => Document(doc)
    case form: jn.FormElement => FormElement(form)
    case pseudo: jn.PseudoTextElement => PseudoTextElement(pseudo)
    case _ => new Element(elem)
  }

  def apply(tag: Tag, baseUri: String, attributes: Attributes = null): Element =
    Element(new jn.Element(tag.asJsoup, baseUri, if (attributes == null) null else attributes.asJsoup))

  def apply(tag: String): Element = new Element(new jn.Element(tag))
}
