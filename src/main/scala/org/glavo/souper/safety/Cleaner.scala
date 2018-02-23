package org.glavo.souper.safety

import org.glavo.souper.nodes.Document
import org.glavo.souper.Implicits._
import org.jsoup.{safety => jsafety}

final class Cleaner(val delegate: jsafety.Cleaner) {
  def clean(dirtyDocument: Document): Document = delegate.clean(dirtyDocument.delegate)

  def isValid(dirtyDocument: Document): Boolean = delegate.isValid(dirtyDocument.delegate)

  def isValidBodyHtml(bodyHtml: String): Boolean = delegate.isValidBodyHtml(bodyHtml)
}

object Cleaner {
  def apply(cleaner: jsafety.Cleaner): Cleaner = new Cleaner(cleaner)
}