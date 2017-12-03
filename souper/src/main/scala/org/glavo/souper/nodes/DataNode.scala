package org.glavo.souper.nodes

import org.jsoup.{nodes => js}

final class DataNode private(override val asJsoup: js.DataNode) extends LeafNode {
  def wholeData: String = asJsoup.getWholeData

  def wholeData_=(data: String): Unit = asJsoup.setWholeData(data)

  def setWholeData(data: String): DataNode = DataNode(asJsoup.setWholeData(data))
}

object DataNode {
  def apply(dataNode: js.DataNode): DataNode = if(dataNode == null) null else new DataNode(dataNode)

  def apply(data: String): DataNode = DataNode(new js.DataNode(data))

  def createFromEncoded(encodedData: String, baseUri: String = null): DataNode =
    DataNode(js.DataNode.createFromEncoded(encodedData, baseUri))
}