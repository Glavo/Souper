package org.glavo.souper.parser

import java.io.Reader

import org.glavo.souper.nodes._
import org.jsoup.{parser => jp}

final class Parser(val delegate: jp.Parser) {
  def parseInput(html: String, baseUri: String): Document =
    org.glavo.souper.nodes.Implicits.documentWrapper(delegate.parseInput(html, baseUri))

  def parseInput(inputHtml: Reader, baseUri: String): Document =
    org.glavo.souper.nodes.Implicits.documentWrapper(delegate.parseInput(inputHtml, baseUri))

  def isTrackErrors: Boolean = delegate.isTrackErrors

  def trackErrors(maxErrors: Int): Parser.this.type = {
    delegate.setTrackErrors(maxErrors)
    this
  }

  def errors: ParseErrorList = ParseErrorList(delegate.getErrors.asInstanceOf[jp.ParseErrorList])

  def settings: ParseSettings = delegate.settings()

  def settings_=(settings: ParseSettings): Unit = delegate.settings(settings)

  def settings(settings: ParseSettings): Parser.this.type = {
    delegate.settings(settings)
    this
  }

  override def toString: String = delegate.toString
}

object Parser {
  def apply(parser: jp.Parser): Parser = if (parser == null) null else new Parser(parser)

  def parse(html: String, baseUri: String): Document =
    org.glavo.souper.nodes.Implicits.documentWrapper(jp.Parser.parse(html, baseUri))

  val htmlParser: Parser = new Parser(jp.Parser.htmlParser())
  val xmlParser: Parser = new Parser(jp.Parser.xmlParser())

  val defaultParser: Parser = htmlParser
}
