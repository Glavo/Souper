package org.glavo.souper.safety

import org.jsoup.{safety => jsafety}

import scala.language.implicitConversions

trait Implicits {
  implicit final def whiteListWrapper(whitelist: jsafety.Whitelist): Whitelist = Whitelist(whitelist)

  implicit final def cleanerWrapper(cleaner: jsafety.Cleaner): Cleaner = Cleaner(cleaner)
}

object Implicits extends Implicits
