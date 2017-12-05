package org.glavo.souper.parser

import org.jsoup.{parser => jp}

final class Tag private(val asJsoup: jp.Tag) extends AnyVal {
  @inline
  def name: String = asJsoup.getName

  @inline
  override def toString: String = asJsoup.toString
}

object Tag {
  @inline
  def apply(tag: jp.Tag): Tag = new Tag(tag)

  @inline
  def apply(tagName: String, settings: ParseSettings = ParseSettings.preserveCase): Tag =
    Tag(jp.Tag.valueOf(tagName, settings))
}
