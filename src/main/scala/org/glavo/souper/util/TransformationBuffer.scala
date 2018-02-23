package org.glavo.souper.util

import scala.collection.mutable
import scala.collection.JavaConverters._

class TransformationBuffer[E, F](val source: mutable.Buffer[F], val transformer: Transformer[E, F])
  extends mutable.Buffer[E] {
  override def apply(n: Int): E = transformer.transformB(source(n))

  override def update(n: Int, newelem: E): Unit = source(n) = transformer.transformA(newelem)

  override def length: Int = source.length

  override def +=(elem: E): TransformationBuffer.this.type = {
    source += transformer.transformA(elem)
    this
  }

  override def clear(): Unit = source.clear()

  override def +=:(elem: E): TransformationBuffer.this.type = {
    source.+=:(transformer.transformA(elem))
    this
  }

  override def insertAll(n: Int, elems: Traversable[E]): Unit = {
    source.insertAll(n, elems.view.map(transformer.transformA))
  }

  override def remove(n: Int): E = transformer.transformB(source.remove(n))

  override def iterator: Iterator[E] = new TransformationIterator[E, F](source.iterator, transformer.transformB)
}

object TransformationBuffer {
  def fromJavaList[E, F](list: java.util.List[F], transformer: Transformer[E, F]): TransformationBuffer[E, F] =
    new TransformationBuffer[E, F](list.asScala, transformer)
}
