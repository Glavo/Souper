package org.glavo.souper.parser

import org.jsoup.{parser => jp}

final class ParseError(val delegate: jp.ParseError) extends AnyVal {
  @inline
  def errorMessage: String = delegate.getErrorMessage

  @inline
  def position: Int = delegate.getPosition

  override def toString: String = delegate.toString
}

object ParseError {
  @inline
  def apply(error: jp.ParseError): ParseError = new ParseError(error)
}