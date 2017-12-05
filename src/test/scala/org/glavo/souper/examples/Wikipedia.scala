package org.glavo.souper.examples

import org.glavo.souper.Souper

/** A simple example, used on the jsoup website. */
object Wikipedia {
  def main(args: Array[String]): Unit = {
    val doc = Souper.connect("http://en.wikipedia.org/").get()
    println(doc.title)

    doc css "#mp-itn b a" foreach (it => println(s"${it attr "title"}\n\t${it absUrl "href"}"))
  }
}
