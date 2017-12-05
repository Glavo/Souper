package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

final class DocumentType private(override val asJsoup: jn.DocumentType) extends LeafNode {
  def pubSysKey: String = asJsoup.attr(DocumentType.PubSysKey)

  def pubSysKey_=(value: String): Unit = asJsoup.setPubSysKey(value)
}

object DocumentType {
  val PublicKey: String = jn.DocumentType.PUBLIC_KEY

  val SystemKey: String = jn.DocumentType.SYSTEM_KEY

  private val PubSysKey = "pubSysKey"

  def apply(docType: jn.DocumentType): DocumentType = if (docType == null) null else new DocumentType(docType)

  def apply(name: String, publicId: String, systemId: String): DocumentType =
    new DocumentType(new jn.DocumentType(name, publicId, systemId))
}
