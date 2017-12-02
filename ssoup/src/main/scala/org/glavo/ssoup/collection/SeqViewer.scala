package org.glavo.ssoup.collection

trait SeqViewer[O, N] extends scala.collection .Seq[N]{
  val oldSeq: scala.collection.Seq[O]

  def converter(elem: O): N

  override def length = oldSeq.size

  override def apply(idx: Int) = converter(oldSeq(idx))

  override def iterator: Iterator[N] = new Iterator[N] {
    val it: Iterator[O] = oldSeq.iterator

    override def hasNext = it.hasNext

    override def next() = converter(it.next())
  }
}
