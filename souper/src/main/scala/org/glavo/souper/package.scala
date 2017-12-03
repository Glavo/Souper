package org.glavo

package object souper {
  type Connection = org.jsoup.Connection

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

    type Base[T <: org.jsoup.Connection.Base[_]] = org.jsoup.Connection.Base[T]
    type Request = org.jsoup.Connection.Request
    type Response = org.jsoup.Connection.Response
    type KeyVal = org.jsoup.Connection.KeyVal

  }

}
