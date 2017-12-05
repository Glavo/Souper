package org.glavo.souper

import org.glavo.souper.nodes._

package object safety {
  type Whitelist = org.jsoup.safety.Whitelist

  type Cleaner = org.jsoup.safety.Cleaner

  implicit final class RichCleaner(val asJsoup: Cleaner) extends AnyVal {
    @inline
    def clean(dirtyDocument: Document): Document = asJsoup.clean(dirtyDocument.asJsoup).asSouper

    @inline
    def isValid(dirtyDocument: Document): Boolean = asJsoup.isValid(dirtyDocument.asJsoup)
  }

}
