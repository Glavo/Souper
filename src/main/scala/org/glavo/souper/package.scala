package org.glavo

import org.jsoup

package object souper {

  type HttpStatusException = org.jsoup.HttpStatusException

  type SerializationException = org.jsoup.SerializationException

  type UncheckedIOException = org.jsoup.UncheckedIOException

  type UnsupportedMimeTypeException = org.jsoup.UnsupportedMimeTypeException

  implicit class RichConnection(val connection: jsoup.Connection) extends AnyVal {
    @inline
    def asSouper: Connection = Connection(connection)
  }

  implicit class RichBase(val base: jsoup.Connection.Base[_]) extends AnyVal {
    @inline
    def asSouper: Connection.Base = Connection.Base(base)
  }

  implicit class RichRequest(val request: jsoup.Connection.Request) extends AnyVal {
    @inline
    def asSouper: Connection.Request = Connection.Request(request)
  }

  implicit class RichResponse(val response: jsoup.Connection.Response) extends AnyVal {
    @inline
    def asSouper: Connection.Response = Connection.Response(response)
  }

  implicit class RichKeyVal(val keyval: jsoup.Connection.KeyVal) extends AnyVal {
    @inline
    def asSouper: Connection.KeyVal = Connection.KeyVal(keyval)
  }
}
