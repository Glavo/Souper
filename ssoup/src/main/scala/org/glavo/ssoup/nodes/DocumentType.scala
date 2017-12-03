package org.glavo.ssoup.nodes

import org.jsoup.{nodes => js}

final class DocumentType private(override val asJsoup: js.DocumentType) extends LeafNode {
  def pubSysKey: String = asJsoup.attr(DocumentType.PubSysKey)

  def pubSysKey_=(value: String): Unit = asJsoup.setPubSysKey(value)
}

object DocumentType {
  val PublicKey: String = js.DocumentType.PUBLIC_KEY

  val SystemKey: String = js.DocumentType.SYSTEM_KEY

  private val PubSysKey = "pubSysKey"

  def apply(docType: js.DocumentType): DocumentType = new DocumentType(docType)
}
