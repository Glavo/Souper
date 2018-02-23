package org.glavo.souper.nodes

import java.nio.charset.Charset

import org.jsoup.{nodes => jn}

final class Document(override val delegate: jn.Document) extends Element(delegate) {
  def location: String = delegate.location()

  def head: Element = Element(delegate.head())

  def body: Element = Element(delegate.body())

  def title: String = delegate.title()

  def title_=(title: String): Unit = delegate.title(title)

  def createElement(tagName: String): Element = Element(delegate.createElement(tagName))

  def normalise(): Document.this.type = {
    delegate.normalise()
    this
  }

  def charset: Charset = delegate.charset()

  def charset_=(charset: Charset): Unit = delegate.charset(charset)

  //noinspection MutatorLikeMethodIsParameterless
  def updateMetaCharsetElement: Boolean = delegate.updateMetaCharsetElement()

  def updateMetaCharsetElement_=(update: Boolean): Unit = delegate.updateMetaCharsetElement(update)

  override def clone(): Document = new Document(delegate.clone())

  def outputSettings: Document.OutputSettings = delegate.outputSettings()

  def outputSettings_=(outputSettings: Document.OutputSettings): Unit = delegate.outputSettings(outputSettings)

  def quirksMode: Document.QuirksMode = delegate.quirksMode()

  def quirksMode_=(quirksMode: Document.QuirksMode): Unit = delegate.quirksMode(quirksMode)
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

  def apply(baseUri: String): Document = Document(new jn.Document(baseUri))

  def createShell(baseUri: String): Document = Document(jn.Document.createShell(baseUri))
}