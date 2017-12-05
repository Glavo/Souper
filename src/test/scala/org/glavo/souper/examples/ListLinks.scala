package org.glavo.souper.examples

import org.glavo.souper._, nodes._

/** Example program to list links from a URL. */
object ListLinks {
  def main(args: Array[String]): Unit = {
    assert(args.length == 1, "usage: supply url to fetch")
    val url = args(0)
    println(s"Fetching $url...")
    val doc = Souper connect url get()
    val links = doc css "a[href]"
    val media = doc css "[src]"
    val imports = doc css "link[href]"

    println(s"\nMedia: (${media.size})")
    media.foreach {
      case src if src.tagName == "img" =>
        println(s" * ${src.tagName}: <${src attr "abs:src"}> ${src attr "width"}x${src attr "height"} (${trim(src attr "alt", 20)})")
      case src =>
        println(s" * ${src.tagName}: <${src attr "abs:src"}>")

    }

    println(s"\nImports: (${imports.size})")
    imports.foreach { link =>
      println(s" * ${link.tagName} <${link attr "abs:href"}> (${link.attr("rel")})")
    }

    println(s"\nLinks: (${links.size})")
    links.foreach { link =>
      println(s" * a: <${link attr "abs:href"}>  (${trim(link.text, 35)})")
    }
  }

  @inline
  private def trim(s: String, width: Int) =
    if (s.length > width) s.substring(0, width - 1) + "."
    else s
}
