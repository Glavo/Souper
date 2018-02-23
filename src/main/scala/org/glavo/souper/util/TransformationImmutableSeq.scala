package org.glavo.souper.util

import scala.collection.JavaConverters._

class TransformationImmutableSeq[+E, F](val source: scala.collection.Seq[F], val transformer: F => E)
  extends scala.collection.immutable.Seq[E] {
  override def length: Int = source.length

  override def apply(idx: Int): E = transformer(source(idx))

  override def iterator: Iterator[E] = new TransformationIterator[E, F](source.iterator, transformer)
}

object TransformationImmutableSeq {
  def fromJavaList[E](list: java.util.List[E]): TransformationImmutableSeq[E, E] =
    new TransformationImmutableSeq(list.asScala, identity[E])

  def fromJavaList[E, F](list: java.util.List[F], transformer: F => E): TransformationImmutableSeq[E, F] =
    new TransformationImmutableSeq(list.asScala, transformer)
}