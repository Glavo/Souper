package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

final class DataNode private(override val asJsoup: jn.DataNode) extends LeafNode {
  def wholeData: String = asJsoup.getWholeData

  def wholeData_=(data: String): Unit = asJsoup.setWholeData(data)

  def setWholeData(data: String): DataNode = DataNode(asJsoup.setWholeData(data))
}

object DataNode {
  def apply(dataNode: jn.DataNode): DataNode = if(dataNode == null) null else new DataNode(dataNode)

  def apply(data: String): DataNode = new DataNode(new jn.DataNode(data))

  def createFromEncoded(encodedData: String, baseUri: String = null): DataNode =
    DataNode(jn.DataNode.createFromEncoded(encodedData, baseUri))
}