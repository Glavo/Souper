package org.glavo.souper.nodes

import org.jsoup.{nodes => js}

object Entities {
  type EscapeMode = js.Entities.EscapeMode

  object EscapeMode {
    val xhtml: EscapeMode = js.Entities.EscapeMode.xhtml

    val base: EscapeMode = js.Entities.EscapeMode.base

    val extended: EscapeMode = js.Entities.EscapeMode.extended
  }

}
