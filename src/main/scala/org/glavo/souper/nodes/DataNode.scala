package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

final class DataNode(override val delegate: jn.DataNode) extends LeafNode {
  def wholeData: String = delegate.getWholeData

  def wholeData_=(data: String): Unit = delegate.setWholeData(data)

  def setWholeData(data: String): DataNode = new DataNode(delegate.setWholeData(data))
}

object DataNode {
  def apply(dataNode: jn.DataNode): DataNode = if(dataNode == null) null else new DataNode(dataNode)

  def apply(data: String): DataNode = new DataNode(new jn.DataNode(data))

  def createFromEncoded(encodedData: String, baseUri: String = null): DataNode =
    DataNode(jn.DataNode.createFromEncoded(encodedData, baseUri))
}