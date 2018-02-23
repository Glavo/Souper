package org.glavo.souper.util

import scala.collection.JavaConverters._

class TransformationIterator[+E, F](val source: Iterator[F], val transformer: F => E)
  extends Iterator[E] {
  override def hasNext: Boolean = source.hasNext

  override def next(): E = transformer(source.next())
}

object TransformationIterator {
  def fromJava[E, F](source: java.util.Iterator[F], transformer: F => E): TransformationIterator[E, F] =
    new TransformationIterator(source.asScala, transformer)
}