package org.glavo.souper

import org.jsoup.{parser => jp}

import scala.language.implicitConversions

package object parser {
  type ParseSettings = jp.ParseSettings

  object ParseSettings {
    val htmlDefault: ParseSettings = jp.ParseSettings.htmlDefault

    val preserveCase: ParseSettings = jp.ParseSettings.preserveCase
  }

  @inline
  implicit def wrapTag(tag: jp.Tag): Tag = Tag(tag)

  @inline
  implicit def unwrapTag(tag: Tag): jp.Tag = tag.asJsoup

  implicit class RichTag(val asJsoup: jp.Tag) extends AnyVal {
    @inline
    def asSouper: Tag = Tag(asJsoup)
  }

  implicit class RichParser(val asJsoup: jp.Parser) extends AnyVal {
    @inline
    def asSouper: Parser = Parser(asJsoup)
  }

  implicit class RichParseError(val asJsoup: jp.ParseError) extends AnyVal {
    @inline
    def asSouper: ParseError = ParseError(asJsoup)
  }

  implicit class RichParseErrorList(val asJsoup: jp.ParseErrorList) extends AnyVal {
    @inline
    def asSouper: ParseErrorList = ParseErrorList(asJsoup)
  }

}
