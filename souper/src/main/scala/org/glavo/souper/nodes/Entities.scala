package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

object Entities {
  type EscapeMode = jn.Entities.EscapeMode

  object EscapeMode {
    val xhtml: EscapeMode = jn.Entities.EscapeMode.xhtml

    val base: EscapeMode = jn.Entities.EscapeMode.base

    val extended: EscapeMode = jn.Entities.EscapeMode.extended
  }

}
