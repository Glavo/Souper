package org.glavo.souper.nodes

import java.nio.charset.Charset

import org.jsoup.{nodes => jn}

final class Document(override val asJsoup: jn.Document) extends Element(asJsoup) {
  def location: String = asJsoup.location()

  def head: Element = Element(asJsoup.head())

  def body: Element = Element(asJsoup.body())

  def title: String = asJsoup.title()

  def title_=(title: String): Unit = asJsoup.title(title)

  def createElement(tagName: String): Element = Element(asJsoup.createElement(tagName))

  def normalise(): Document.this.type = {
    asJsoup.normalise()
    this
  }

  def charset: Charset = asJsoup.charset()

  def charset_=(charset: Charset): Unit = asJsoup.charset(charset)

  //noinspection MutatorLikeMethodIsParameterless
  def updateMetaCharsetElement: Boolean = asJsoup.updateMetaCharsetElement()

  def updateMetaCharsetElement_=(update: Boolean): Unit = asJsoup.updateMetaCharsetElement(update)

  override def clone(): Document = Document(asJsoup.clone())

  def outputSettings: Document.OutputSettings = asJsoup.outputSettings()

  def outputSettings_=(outputSettings: Document.OutputSettings): Unit = asJsoup.outputSettings(outputSettings)

  def quirksMode: Document.QuirksMode = asJsoup.quirksMode()

  def quirksMode_=(quirksMode: Document.QuirksMode): Unit = asJsoup.quirksMode(quirksMode)
}

object Document {
  type OutputSettings = jn.Document.OutputSettings

  object OutputSettings {
    type Syntax = jn.Document.OutputSettings.Syntax

    object Syntax {
      val html: Syntax = jn.Document.OutputSettings.Syntax.html

      val xml: Syntax = jn.Document.OutputSettings.Syntax.xml
    }

  }

  type QuirksMode = jn.Document.QuirksMode

  object QuirksMode {
    val noQuirks: QuirksMode = jn.Document.QuirksMode.noQuirks
    val quirks: QuirksMode = jn.Document.QuirksMode.quirks
    val limitedQuirks: QuirksMode = jn.Document.QuirksMode.limitedQuirks
  }

  def apply(doc: jn.Document): Document = if(doc == null) null else new Document(doc)

  def apply(baseUri: String): Document = new Document(new jn.Document(baseUri))

  def createShell(baseUri: String): Document = Document(jn.Document.createShell(baseUri))
}