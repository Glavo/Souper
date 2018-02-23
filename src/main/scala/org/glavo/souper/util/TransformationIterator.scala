package org.glavo.souper.util

class TransformationIterator[+E, +F](val source: Iterator[F], val transformer: F => E)
  extends Iterator[E] {
  override def hasNext: Boolean = source.hasNext

  override def next(): E = transformer(source.next())
}
