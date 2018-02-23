package org.glavo.souper

import java.io.{BufferedInputStream, InputStream}
import java.net.{Proxy, URL}

import org.glavo.souper.nodes._
import org.glavo.souper.parser.Parser
import org.glavo.souper.util.{TransformationIterator, Transformer}
import org.jsoup

import scala.collection.JavaConverters._
import scala.collection.mutable

final class Connection(val delegate: jsoup.Connection) {
  type Self = Connection.this.type

  def url(url: URL): Self = {
    delegate.url(url)
    this
  }

  def url(url: String): Self = {
    delegate.url(url)
    this
  }

  def proxy(proxy: Proxy): Self = {
    delegate.proxy(proxy)
    this
  }

  def proxy(host: String, port: Int): Self = {
    delegate.proxy(host, port)
    this
  }

  def userAgent(userAgent: String): Self = {
    delegate.userAgent(userAgent)
    this
  }

  def timeout(millis: Int): Self = {
    delegate.timeout(millis)
    this
  }

  def maxBodySize(bytes: Int): Self = {
    delegate.maxBodySize(bytes)
    this
  }

  def referrer(referrer: String): Self = {
    delegate.referrer(referrer)
    this
  }

  def followRedirects(followRedirects: Boolean): Self = {
    delegate.followRedirects(followRedirects)
    this
  }

  def method(method: Connection.Method): Self = {
    delegate.method(method)
    this
  }

  def ignoreHttpErrors(ignoreHttpErrors: Boolean): Self = {
    delegate.ignoreHttpErrors(ignoreHttpErrors)
    this
  }

  def ignoreContentType(ignoreContentType: Boolean): Self = {
    delegate.ignoreContentType(ignoreContentType)
    this
  }

  def data(key: String, value: String): Self = {
    delegate.data(key, value)
    this
  }

  def data(key: String, filename: String, inputStream: InputStream): Self = {
    delegate.data(key, filename, inputStream)
    this
  }

  def data(key: String, filename: String, inputStream: InputStream, contentType: String): Self = {
    delegate.data(key, filename, inputStream, contentType)
    this
  }

  def data(data: Iterable[Connection.KeyVal]): Self = {
    delegate.data(data.view.map(_.delegate).asJavaCollection)
    this
  }

  def data(data: Map[String, String]): Self = {
    delegate.data(data.asJava)
    this
  }

  def data(keyvals: String*): Self = {
    delegate.data(keyvals: _*)
    this
  }

  def data(key: String): Connection.KeyVal = Connection.KeyVal(delegate.data(key))

  def requestBody(body: String): Self = {
    delegate.requestBody(body)
    this
  }

  def header(name: String, value: String): Self = {
    delegate.header(name, value)
    this
  }

  def headers(headers: Map[String, String]): Self = {
    delegate.headers(headers.asJava)
    this
  }

  def cookie(name: String, value: String): Self = {
    delegate.cookie(name, value)
    this
  }

  def cookies(cookies: Map[String, String]): Self = {
    delegate.cookies(cookies.asJava)
    this
  }

  def parser(parser: Parser): Self = {
    delegate.parser(parser.delegate)
    this
  }

  def postDataCharset(charset: String): Self = {
    delegate.postDataCharset(charset)
    this
  }

  def get(): Document = nodes.Implicits.documentWrapper(delegate.get())

  def post(): Document = nodes.Implicits.documentWrapper(delegate.post())

  def execute(): Connection.Response = Connection.Response(delegate.execute())

  def request(): Connection.Request = Connection.Request(delegate.request())

  def request(request: Connection.Request): Self = {
    delegate.request(request.delegate)
    this
  }

  def response(): Connection.Response = Connection.Response(delegate.response())

  def response(response: Connection.Response): Self = {
    delegate.response(response.delegate)
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

  class Base(val delegate: jsoup.Connection.Base[_]) {
    def url: URL = delegate.url()

    def url_=(url: URL): Unit = delegate.url(url)

    def url(url: URL): Base.this.type = {
      delegate.url(url)
      this
    }

    def method: Method = delegate.method()

    def method_=(method: Method): Unit = delegate.method(method)

    def method(method: Method): Base.this.type = {
      delegate.method(method)
      this
    }

    def header(name: String): String = delegate.header(name)

    def headers(name: String): mutable.Buffer[String] = delegate.headers(name).asScala

    def header(name: String, value: String): Base.this.type = {
      delegate.header(name, value)
      this
    }

    def addHeader(name: String, value: String): Base.this.type = {
      delegate.addHeader(name, value)
      this
    }

    def hasHeader(name: String): Boolean = delegate.hasHeader(name)

    def hasHeaderWithValue(name: String, value: String): Boolean = delegate.hasHeaderWithValue(name, value)

    def removeHeader(name: String): Base.this.type = {
      delegate.removeHeader(name)
      this
    }

    def allHeaders: mutable.Map[String, String] = delegate.headers().asScala

    def multiHeaders: mutable.Map[String, mutable.Buffer[String]] = new mutable.Map[String, mutable.Buffer[String]] {
      private val headers = delegate.multiHeaders()

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

      override def iterator: Iterator[(String, mutable.Buffer[String])] =
        TransformationIterator.fromJava[(String, mutable.Buffer[String]), java.util.Map.Entry[String, java.util.List[String]]](
          headers.entrySet().iterator(),
          it => (it.getKey, it.getValue.asScala)
        )
    }

    def cookie(name: String): String = delegate.cookie(name)

    def cookie(name: String, value: String): Base.this.type = {
      delegate.cookie(name, value)
      this
    }

    def hasCookie(name: String): Boolean = delegate.hasCookie(name)

    def removeCookie(name: String): Base.this.type = {
      delegate.removeCookie(name)
      this
    }

    def cookies(): mutable.Map[String, String] = delegate.cookies().asScala
  }

  object Base {
    @inline
    def apply(base: jsoup.Connection.Base[_]): Base = if (base == null) null else new Base(base)
  }

  class Request(override val delegate: jsoup.Connection.Request) extends Base(delegate) {

    def proxy: Proxy = delegate.proxy()

    def proxy_=(proxy: Proxy): Unit =
      delegate.proxy(proxy)

    def proxy(proxy: Proxy): Request.this.type = {
      delegate.proxy(proxy)
      this
    }

    def proxy(host: String, port: Int): Request.this.type = {
      delegate.proxy(host, port)
      this
    }

    def timeout: Int = delegate.timeout()

    def timeout_=(millis: Int): Unit = delegate.timeout(millis)

    def timeout(millis: Int): Request.this.type = {
      delegate.timeout(millis)
      this
    }

    def maxBodySize: Int = delegate.maxBodySize()

    def maxBodySize_=(bytes: Int): Unit =
      delegate.maxBodySize(bytes)

    def maxBodySize(bytes: Int): Request.this.type = {
      delegate.maxBodySize(bytes)
      this
    }

    def followRedirects: Boolean = delegate.followRedirects()

    def followRedirects_=(followRedirects: Boolean): Unit = delegate.followRedirects(followRedirects)

    def followRedirects(followRedirects: Boolean): Request.this.type = {
      delegate.followRedirects(followRedirects)
      this
    }

    def ignoreHttpErrors: Boolean = delegate.ignoreHttpErrors()

    def ignoreHttpErrors_=(ignoreHttpErrors: Boolean): Unit = delegate.ignoreHttpErrors(ignoreHttpErrors)

    def ignoreHttpErrors(ignoreHttpErrors: Boolean): Request.this.type = {
      delegate.ignoreHttpErrors(ignoreHttpErrors)
      this
    }

    def ignoreContentType: Boolean = delegate.ignoreContentType()

    def ignoreContentType_=(ignoreContentType: Boolean): Unit = delegate.ignoreContentType(ignoreContentType)

    def ignoreContentType(ignoreContentType: Boolean): Request.this.type = {
      delegate.ignoreContentType(ignoreContentType)
      this
    }

    def data: Iterable[KeyVal] = delegate.data().asScala.view.map(Connection.KeyVal.apply)

    def data(keyval: KeyVal): Request.this.type = {
      delegate.data(keyval.delegate)
      this
    }

    def requestBody: String = delegate.requestBody()

    def requestBody_=(body: String): Unit = delegate.requestBody(body)

    def requestBody(body: String): Request.this.type = {
      delegate.requestBody(body)
      this
    }

    def parser: Parser = Parser(delegate.parser)

    def parser_=(parser: Parser): Unit = delegate.parser(parser.delegate)

    def parser(parser: Parser): Request.this.type = {
      delegate.parser(parser.delegate)
      this
    }

    def postDataCharset: String = delegate.postDataCharset()

    def postDataCharset_=(charset: String): Unit = delegate.postDataCharset(charset)

    def postDataCharset(charset: String): Request.this.type = {
      delegate.postDataCharset(charset)
      this
    }
  }

  object Request {
    @inline
    def apply(request: jsoup.Connection.Request): Request = if (request == null) null else new Request(request)
  }

  final class Response(override val delegate: jsoup.Connection.Response) extends Base(delegate) {
    def statusCode: Int = delegate.statusCode()

    def statusMessage: String = delegate.statusMessage()

    def charset: String = delegate.charset()

    def charset_=(charset: String): Unit = delegate.charset(charset)

    def charset(charset: String): Response.this.type = {
      delegate.charset(charset)
      this
    }

    def contentType: String = delegate.contentType()

    def parse: Document = nodes.Implicits.documentWrapper(delegate.parse())

    def body: String = delegate.body()

    def bodyAsBytes: Array[Byte] = delegate.bodyAsBytes()

    def bufferUp: Response = new Response(delegate.bufferUp())

    def bodyStream: BufferedInputStream = delegate.bodyStream()
  }

  object Response {
    @inline
    def apply(response: jsoup.Connection.Response): Response = new Response(response)
  }

  final class KeyVal(val delegate: jsoup.Connection.KeyVal) {

    def key: String = delegate.key()

    def key_=(key: String): Unit = delegate.key(key)

    def key(key: String): KeyVal.this.type = {
      delegate.key(key)
      this
    }

    def value: String = delegate.value()

    def value_=(value: String): Unit = delegate.value(value)

    def value(value: String): KeyVal.this.type = {
      delegate.value(value)
      this
    }

    def inputStream: InputStream = delegate.inputStream()

    def inputStream_=(inputStream: InputStream): Unit = delegate.inputStream(inputStream)

    def inputStream(inputStream: InputStream): KeyVal.this.type = {
      delegate.inputStream(inputStream)
      this
    }

    def hasInputStream: Boolean = delegate.hasInputStream

    def contentType: String = delegate.contentType()

    def contentType_=(contentType: String): Unit = delegate.contentType(contentType)

    def contentType(contentType: String): KeyVal.this.type = {
      delegate.contentType(contentType)
      this
    }

    override def toString: String = delegate.toString
  }

  object KeyVal {
    @inline
    def apply(keyVal: jsoup.Connection.KeyVal): KeyVal = if (keyVal == null) null else new KeyVal(keyVal)
  }

}