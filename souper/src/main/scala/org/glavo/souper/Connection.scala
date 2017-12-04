package org.glavo.souper

import java.io.{BufferedInputStream, InputStream}
import java.net.{Proxy, URL}
import java.util

import org.glavo.souper.nodes._
import org.glavo.souper.parser.Parser
import org.jsoup

import scala.collection.JavaConverters._
import scala.collection.mutable

final class Connection private(val asJsoup: jsoup.Connection) {
  type Self = Connection.this.type

  def url(url: URL): Self = {
    asJsoup.url(url)
    this
  }

  def url(url: String): Self = {
    asJsoup.url(url)
    this
  }

  def proxy(proxy: Proxy): Self = {
    asJsoup.proxy(proxy)
    this
  }

  def proxy(host: String, port: Int): Self = {
    asJsoup.proxy(host, port)
    this
  }

  def userAgent(userAgent: String): Self = {
    asJsoup.userAgent(userAgent)
    this
  }

  def timeout(millis: Int): Self = {
    asJsoup.timeout(millis)
    this
  }

  def maxBodySize(bytes: Int): Self = {
    asJsoup.maxBodySize(bytes)
    this
  }

  def referrer(referrer: String): Self = {
    asJsoup.referrer(referrer)
    this
  }

  def followRedirects(followRedirects: Boolean): Self = {
    asJsoup.followRedirects(followRedirects)
    this
  }

  def method(method: Connection.Method): Self = {
    asJsoup.method(method)
    this
  }

  def ignoreHttpErrors(ignoreHttpErrors: Boolean): Self = {
    asJsoup.ignoreHttpErrors(ignoreHttpErrors)
    this
  }

  def ignoreContentType(ignoreContentType: Boolean): Self = {
    asJsoup.ignoreContentType(ignoreContentType)
    this
  }

  def data(key: String, value: String): Self = {
    asJsoup.data(key, value)
    this
  }

  def data(key: String, filename: String, inputStream: InputStream): Self = {
    asJsoup.data(key, filename, inputStream)
    this
  }

  def data(key: String, filename: String, inputStream: InputStream, contentType: String): Self = {
    asJsoup.data(key, filename, inputStream, contentType)
    this
  }

  def data(data: Iterable[Connection.KeyVal]): Self = {
    asJsoup.data(new util.AbstractCollection[jsoup.Connection.KeyVal] {
      override def iterator(): util.Iterator[jsoup.Connection.KeyVal] = new util.Iterator[jsoup.Connection.KeyVal] {
        private val it = data.iterator

        override def next(): jsoup.Connection.KeyVal = it.next().asJsoup

        override def hasNext: Boolean = it.hasNext
      }

      override def size(): Int = data.size
    })
    this
  }

  def data(data: Map[String, String]): Self = {
    asJsoup.data(data.asJava)
    this
  }

  def data(keyvals: String*): Self = {
    asJsoup.data(keyvals: _*)
    this
  }

  def data(key: String): Connection.KeyVal = Connection.KeyVal(asJsoup.data(key))

  def requestBody(body: String): Self = {
    asJsoup.requestBody(body)
    this
  }

  def header(name: String, value: String): Self = {
    asJsoup.header(name, value)
    this
  }

  def headers(headers: Map[String, String]): Self = {
    asJsoup.headers(headers.asJava)
    this
  }

  def cookie(name: String, value: String): Self = {
    asJsoup.cookie(name, value)
    this
  }

  def cookies(cookies: Map[String, String]): Self = {
    asJsoup.cookies(cookies.asJava)
    this
  }

  def parser(parser: Parser): Self = {
    asJsoup.parser(parser.asJsoup)
    this
  }

  def postDataCharset(charset: String): Self = {
    asJsoup.postDataCharset(charset)
    this
  }

  def get(): Document = asJsoup.get().asSouper

  def post(): Document = asJsoup.post().asSouper

  def execute(): Connection.Response = Connection.Response(asJsoup.execute())

  def request(): Connection.Request = Connection.Request(asJsoup.request())

  def request(request: Connection.Request): Self = {
    asJsoup.request(request.asJsoup)
    this
  }

  def response(): Connection.Response = Connection.Response(asJsoup.response())

  def response(response: Connection.Response): Self = {
    asJsoup.response(response.asJsoup)
    this
  }
}

object Connection {
  def apply(connection: jsoup.Connection): Connection = new Connection(connection)

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

  object Base {
    @inline
    def apply(base: jsoup.Connection.Base[_]): Base = if (base == null) null else new Base(base)
  }

  class Request private(override val asJsoup: jsoup.Connection.Request) extends Base(asJsoup) {

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

    def ignoreContentType: Boolean = asJsoup.ignoreContentType()

    def ignoreContentType_=(ignoreContentType: Boolean): Unit = asJsoup.ignoreContentType(ignoreContentType)

    def ignoreContentType(ignoreContentType: Boolean): Request.this.type = {
      asJsoup.ignoreContentType(ignoreContentType)
      this
    }

    def data: Iterable[KeyVal] = new Iterable[KeyVal] {
      override def iterator: Iterator[KeyVal] = new Iterator[KeyVal] {
        private val it = asJsoup.data().iterator()

        override def hasNext: Boolean = it.hasNext

        override def next(): KeyVal = KeyVal(it.next())
      }
    }

    def data(keyval: KeyVal): Request.this.type = {
      asJsoup.data(keyval.asJsoup)
      this
    }

    def requestBody: String = asJsoup.requestBody()

    def requestBody_=(body: String): Unit = asJsoup.requestBody(body)

    def requestBody(body: String): Request.this.type = {
      asJsoup.requestBody(body)
      this
    }

    def parser: Parser = Parser(asJsoup.parser)

    def parser_=(parser: Parser): Unit = asJsoup.parser(parser.asJsoup)

    def parser(parser: Parser): Request.this.type = {
      asJsoup.parser(parser.asJsoup)
      this
    }

    def postDataCharset: String = asJsoup.postDataCharset()

    def postDataCharset_=(charset: String): Unit = asJsoup.postDataCharset(charset)

    def postDataCharset(charset: String): Request.this.type = {
      asJsoup.postDataCharset(charset)
      this
    }
  }

  object Request {
    @inline
    def apply(request: jsoup.Connection.Request): Request = if (request == null) null else new Request(request)
  }

  final class Response private(override val asJsoup: jsoup.Connection.Response) extends Base(asJsoup) {
    def statusCode: Int = asJsoup.statusCode()

    def statusMessage: String = asJsoup.statusMessage()

    def charset: String = asJsoup.charset()

    def charset_=(charset: String): Unit = asJsoup.charset(charset)

    def charset(charset: String): Response.this.type = {
      asJsoup.charset(charset)
      this
    }

    def contentType: String = asJsoup.contentType()

    def parse: Document = asJsoup.parse().asSouper

    def body: String = asJsoup.body()

    def bodyAsBytes: Array[Byte] = asJsoup.bodyAsBytes()

    def bufferUp: Response = new Response(asJsoup.bufferUp())

    def bodyStream: BufferedInputStream = asJsoup.bodyStream()
  }

  object Response {
    @inline
    def apply(response: jsoup.Connection.Response): Response = new Response(response)
  }

  final class KeyVal private(val asJsoup: jsoup.Connection.KeyVal) {

    def key: String = asJsoup.key()

    def key_=(key: String): Unit = asJsoup.key(key)

    def key(key: String): KeyVal.this.type = {
      asJsoup.key(key)
      this
    }

    def value: String = asJsoup.value()

    def value_=(value: String): Unit = asJsoup.value(value)

    def value(value: String): KeyVal.this.type = {
      asJsoup.value(value)
      this
    }

    def inputStream: InputStream = asJsoup.inputStream()

    def inputStream_=(inputStream: InputStream): Unit = asJsoup.inputStream(inputStream)

    def inputStream(inputStream: InputStream): KeyVal.this.type = {
      asJsoup.inputStream(inputStream)
      this
    }

    def hasInputStream: Boolean = asJsoup.hasInputStream

    def contentType: String = asJsoup.contentType()

    def contentType_=(contentType: String): Unit = asJsoup.contentType(contentType)

    def contentType(contentType: String): KeyVal.this.type = {
      asJsoup.contentType(contentType)
      this
    }

    override def toString: String = asJsoup.toString
  }

  object KeyVal {
    @inline
    def apply(keyVal: jsoup.Connection.KeyVal): KeyVal = if (keyVal == null) null else new KeyVal(keyVal)
  }

}