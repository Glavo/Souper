package org.glavo.souper

trait Implicits
  extends org.glavo.souper.nodes.Implicits
    with org.glavo.souper.parser.Implicits
    with org.glavo.souper.safety.Implicits
    with org.glavo.souper.select.Implicits {

}

object Implicits extends Implicits
