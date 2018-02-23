package org.glavo.souper

import org.glavo.souper.nodes._

package object safety {
  type Whitelist = org.jsoup.safety.Whitelist

  type Cleaner = org.jsoup.safety.Cleaner

  implicit final class RichCleaner(val delegate: Cleaner) extends AnyVal {
    @inline
    def clean(dirtyDocument: Document): Document =
      org.glavo.souper.nodes.Implicits.documentWrapper(delegate.clean(dirtyDocument.delegate))

    @inline
    def isValid(dirtyDocument: Document): Boolean = delegate.isValid(dirtyDocument.delegate)
  }

}
