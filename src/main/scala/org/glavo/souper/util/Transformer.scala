package org.glavo.souper.util

trait Transformer[A, B] {
  self =>
  def transformA(a: A): B

  def transformB(b: B): A

  def reversed: Transformer[B, A] = new Transformer[B, A] {
    override def transformA(a: B): A = self.transformB(a)

    override def transformB(b: A): B = self.transformA(b)
  }
}

object Transformer {
  def apply[A, B](transformerA: A => B, transformerB: B => A): Transformer[A, B] = new Transformer[A, B] {
    override def transformA(a: A): B = transformerA(a)

    override def transformB(b: B): A = transformerB(b)

    override def reversed: Transformer[B, A] = Transformer(transformerB, transformerA)
  }
}
