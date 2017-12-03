package org.glavo.ssoup

import org.jsoup.{parser => jp}

package object parser {
  type ParseSettings = jp.ParseSettings
  object ParseSettings {
    val htmlDefault: ParseSettings = jp.ParseSettings.htmlDefault

    val preserveCase: ParseSettings = jp.ParseSettings.preserveCase
  }
}
