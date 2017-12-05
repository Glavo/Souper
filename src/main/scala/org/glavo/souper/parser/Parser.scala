package org.glavo.souper.parser

import java.io.Reader

import org.glavo.souper.nodes._
import org.jsoup.{parser => jp}

final class Parser private(val asJsoup: jp.Parser) {
  def parseInput(html: String, baseUri: String): Document = asJsoup.parseInput(html, baseUri).asSouper

  def parseInput(inputHtml: Reader, baseUri: String): Document = asJsoup.parseInput(inputHtml, baseUri).asSouper

  def isTrackErrors: Boolean = asJsoup.isTrackErrors

  def trackErrors(maxErrors: Int): Parser.this.type = {
    asJsoup.setTrackErrors(maxErrors)
    this
  }

  def errors: ParseErrorList = ParseErrorList(asJsoup.getErrors.asInstanceOf[jp.ParseErrorList])

  def settings: ParseSettings = asJsoup.settings()

  def settings_=(settings: ParseSettings): Unit = asJsoup.settings(settings)

  def settings(settings: ParseSettings): Parser.this.type = {
    asJsoup.settings(settings)
    this
  }

  override def toString: String = asJsoup.toString
}

object Parser {
  def apply(parser: jp.Parser): Parser = if (parser == null) null else new Parser(parser)

  def parse(html: String, baseUri: String): Document = jp.Parser.parse(html, baseUri).asSouper

  val htmlParser: Parser = new Parser(jp.Parser.htmlParser())
  val xmlParser: Parser = new Parser(jp.Parser.xmlParser())

  val defaultParser: Parser = htmlParser
}
