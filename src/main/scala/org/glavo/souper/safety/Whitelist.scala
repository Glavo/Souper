package org.glavo.souper.safety

import org.jsoup.{safety => jsafety}

class Whitelist(val delegate: jsafety.Whitelist = new jsafety.Whitelist) {
  //todo
}

object Whitelist {
  def apply(whiteList: jsafety.Whitelist = new jsafety.Whitelist): Whitelist = new Whitelist(whiteList)
}