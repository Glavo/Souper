package org.glavo.souper

trait SouperDelegate[+T] extends Any {
  def delegate: T

  override def toString: String = delegate.toString

  override def hashCode: Int = delegate.hashCode()

  override def equals(other: scala.Any): Boolean = other match {
    case sdobj: SouperDelegate[_] => sdobj.delegate.equals(delegate)
    case _ => false
  }
}
