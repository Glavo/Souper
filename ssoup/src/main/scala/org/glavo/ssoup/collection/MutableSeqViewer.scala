package org.glavo.ssoup.collection

trait MutableSeqViewer[O, N] extends SeqViewer[O, N] with scala.collection.mutable.Seq[N] {
  override val oldSeq: scala.collection.mutable.Seq[O]

  def inverseConverter(elem: N): O

  override def update(idx: Int, elem: N): Unit = oldSeq.update(idx, inverseConverter(elem))
}
