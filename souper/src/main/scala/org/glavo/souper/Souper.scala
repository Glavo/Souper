package org.glavo.souper

import java.io.{File, InputStream}

import org.glavo.souper.nodes._
import org.glavo.souper.parser.Parser
import org.jsoup.Jsoup

object Souper {
  def parse(html: String, baseUri: String = "")(implicit parser: Parser): Document =
    Jsoup.parse(html, baseUri, parser.asJsoup).asSouper

  def connect(url: String): Connection = Jsoup.connect(url)

  def parse(in: File, charsetName: String, baseUri: String): Document =
    Jsoup.parse(in, charsetName, baseUri).asSouper

  def parse(in: File, charsetName: String): Document =
    Jsoup.parse(in, charsetName).asSouper

  def parse(in: InputStream, charsetName: String, baseUri: String)(implicit parser: Parser): Document =
    Jsoup.parse(in, charsetName, baseUri, parser.asJsoup).asSouper

  def parseBodyFragment(bodyHtml: String, baseUri: String = ""): Document =
    Jsoup.parseBodyFragment(bodyHtml, baseUri).asSouper
}
