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
}
