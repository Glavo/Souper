package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

object Entities {

  class EscapeMode private(val delegate: jn.Entities.EscapeMode) {
    override def hashCode(): Int = delegate.hashCode()

    override def toString: String = delegate.toString
  }

  object EscapeMode {
    val xhtml: EscapeMode = new EscapeMode(jn.Entities.EscapeMode.xhtml)

    val base: EscapeMode = new EscapeMode(jn.Entities.EscapeMode.base)

    val extended: EscapeMode = new EscapeMode(jn.Entities.EscapeMode.extended)

    val values: Seq[EscapeMode] = Seq(xhtml, base, extended)

    def valueOf(name: String): EscapeMode = name match {
      case "xhtml" => xhtml
      case "base" => base
      case "extended" => extended
    }
  }

}
