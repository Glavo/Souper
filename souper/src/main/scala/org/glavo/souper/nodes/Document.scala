package org.glavo.souper.nodes

import java.nio.charset.Charset

import org.jsoup.{nodes => js}

final class Document(override val asJsoup: js.Document) extends Element(asJsoup) {
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
  type OutputSettings = js.Document.OutputSettings

  object OutputSettings {
    type Syntax = js.Document.OutputSettings.Syntax

    object Syntax {
      val html: Syntax = js.Document.OutputSettings.Syntax.html

      val xml: Syntax = js.Document.OutputSettings.Syntax.xml
    }

  }

  type QuirksMode = js.Document.QuirksMode

  object QuirksMode {
    val noQuirks: QuirksMode = js.Document.QuirksMode.noQuirks
    val quirks: QuirksMode = js.Document.QuirksMode.quirks
    val limitedQuirks: QuirksMode = js.Document.QuirksMode.limitedQuirks
  }

  def apply(doc: js.Document): Document = if(doc == null) null else new Document(doc)

  def apply(baseUri: String): Document = new Document(new js.Document(baseUri))

  def createShell(baseUri: String): Document = Document(js.Document.createShell(baseUri))
}