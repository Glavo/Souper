package org.glavo.souper

import java.io.{BufferedInputStream, InputStream}
import java.net.{Proxy, URL}
import java.util

import org.glavo.souper.nodes.Document
import org.glavo.souper.parser.Parser
import org.jsoup

import scala.collection.JavaConverters._
import scala.collection.mutable

class Connection(val asJsoup: jsoup.Connection) extends AnyVal {

}

object Connection {
  type Method = org.jsoup.Connection.Method

  object Method {
    val Get: Connection.Method = org.jsoup.Connection.Method.GET
    val Post: Connection.Method = org.jsoup.Connection.Method.POST
    val Put: Connection.Method = org.jsoup.Connection.Method.PUT
    val Delete: Connection.Method = org.jsoup.Connection.Method.DELETE
    val Patch: Connection.Method = org.jsoup.Connection.Method.PATCH
    val Head: Connection.Method = org.jsoup.Connection.Method.HEAD
    val Options: Connection.Method = org.jsoup.Connection.Method.OPTIONS
    val Trace: Connection.Method = org.jsoup.Connection.Method.TRACE
  }

  class Base(val asJsoup: jsoup.Connection.Base[_]) {
    def url: URL = asJsoup.url()

    def url_=(url: URL): Unit = asJsoup.url(url)

    def url(url: URL): Base.this.type = {
      asJsoup.url(url)
      this
    }

    def method: Method = asJsoup.method()

    def method_=(method: Method): Unit = asJsoup.method(method)

    def method(method: Method): Base.this.type = {
      asJsoup.method(method)
      this
    }

    def header(name: String): String = asJsoup.header(name)

    def headers(name: String): mutable.Buffer[String] = asJsoup.headers(name).asScala

    def header(name: String, value: String): Base.this.type = {
      asJsoup.header(name, value)
      this
    }

    def addHeader(name: String, value: String): Base.this.type = {
      asJsoup.addHeader(name, value)
      this
    }

    def hasHeader(name: String): Boolean = asJsoup.hasHeader(name)

    def hasHeaderWithValue(name: String, value: String): Boolean = asJsoup.hasHeaderWithValue(name, value)

    def removeHeader(name: String): Base.this.type = {
      asJsoup.removeHeader(name)
      this
    }

    def allHeaders: mutable.Map[String, String] = asJsoup.headers().asScala

    def multiHeaders: mutable.Map[String, mutable.Buffer[String]] = new mutable.Map[String, mutable.Buffer[String]] {
      private val headers = asJsoup.multiHeaders()

      override def +=(kv: (String, mutable.Buffer[String])): this.type = {
        headers.put(kv._1, kv._2.asJava)
        this
      }

      override def -=(key: String): this.type = {
        headers.remove(key)
        this
      }

      override def get(key: String): Option[mutable.Buffer[String]] = {
        Option(headers.getOrDefault(key, null)).map(_.asScala)
      }

      override def iterator: Iterator[(String, mutable.Buffer[String])] = new Iterator[(String, mutable.Buffer[String])] {
        private val it = headers.entrySet().iterator()

        override def hasNext: Boolean = it.hasNext

        override def next(): (String, mutable.Buffer[String]) = {
          val e = it.next()
          (e.getKey, e.getValue.asScala)
        }
      }
    }

    def cookie(name: String): String = asJsoup.cookie(name)

    def cookie(name: String, value: String): Base.this.type = {
      asJsoup.cookie(name, value)
      this
    }

    def hasCookie(name: String): Boolean = asJsoup.hasCookie(name)

    def removeCookie(name: String): Base.this.type = {
      asJsoup.removeCookie(name)
      this
    }

    def cookies(): mutable.Map[String, String] = asJsoup.cookies().asScala
  }

  class Request(override val asJsoup: jsoup.Connection.Request) extends Base(asJsoup) {

    def proxy: Proxy = asJsoup.proxy()

    def proxy_=(proxy: Proxy): Unit =
      asJsoup.proxy(proxy)

    def proxy(proxy: Proxy): Request.this.type = {
      asJsoup.proxy(proxy)
      this
    }

    def proxy(host: String, port: Int): Request.this.type = {
      asJsoup.proxy(host, port)
      this
    }

    def timeout: Int = asJsoup.timeout()

    def timeout_=(millis: Int): Unit = asJsoup.timeout(millis)

    def timeout(millis: Int): Request.this.type = {
      asJsoup.timeout(millis)
      this
    }

    def maxBodySize: Int = asJsoup.maxBodySize()

    def maxBodySize_=(bytes: Int): Unit =
      asJsoup.maxBodySize(bytes)

    def maxBodySize(bytes: Int): Request.this.type = {
      asJsoup.maxBodySize(bytes)
      this
    }

    def followRedirects: Boolean = asJsoup.followRedirects()

    def followRedirects_=(followRedirects: Boolean): Unit = asJsoup.followRedirects(followRedirects)

    def followRedirects(followRedirects: Boolean): Request.this.type = {
      asJsoup.followRedirects(followRedirects)
      this
    }

    def ignoreHttpErrors: Boolean = asJsoup.ignoreHttpErrors()

    def ignoreHttpErrors_=(ignoreHttpErrors: Boolean): Unit = asJsoup.ignoreHttpErrors(ignoreHttpErrors)

    def ignoreHttpErrors(ignoreHttpErrors: Boolean): Request.this.type = {
      asJsoup.ignoreHttpErrors(ignoreHttpErrors)
      this
    }

    def ignoreContentType: Boolean = asJsoup.ignoreHttpErrors()

    def ignoreContentType_=(ignoreContentType: Boolean): Unit = asJsoup.ignoreContentType(ignoreContentType)

    def ignoreContentType(ignoreContentType: Boolean): Request.this.type = {
      asJsoup.ignoreContentType(ignoreContentType)
      this
    }

  }

}