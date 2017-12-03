package org.glavo.ssoup.parser

import org.jsoup.{parser => jp}

final class Tag(val asJsoup: jp.Tag) extends AnyVal {
  def name: String = asJsoup.getName

  override def toString: String = asJsoup.toString
  //todo
}

object Tag {
  def apply(tag: jp.Tag): Tag = new Tag(tag)

  def apply(tagName: String, settings: ParseSettings = ParseSettings.preserveCase): Tag =
    Tag(jp.Tag.valueOf(tagName, settings))
}
