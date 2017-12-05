package org.glavo.souper.parser

import org.jsoup.{parser => jp}

final class ParseError private(val asJsoup: jp.ParseError) extends AnyVal {
  @inline
  def errorMessage: String = asJsoup.getErrorMessage

  @inline
  def position: Int = asJsoup.getPosition

  override def toString: String = asJsoup.toString
}

object ParseError {
  @inline
  def apply(error: jp.ParseError): ParseError = new ParseError(error)
}