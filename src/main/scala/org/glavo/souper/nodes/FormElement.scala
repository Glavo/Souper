package org.glavo.souper.nodes

import org.glavo.souper._
import org.glavo.souper.select.Elements
import org.glavo.souper.util.{TransformationBuffer, Transformer}
import org.jsoup.{nodes => jn}

import scala.collection.mutable

class FormElement(override val delegate: jn.FormElement) extends Element(delegate) {
  def elements: Elements = Elements(delegate.elements())

  def addElement(element: Element): FormElement.this.type = {
    delegate.addElement(element.delegate)
    this
  }

  def submit: Connection = Connection(delegate.submit())

  def formData: mutable.Buffer[Connection.KeyVal] =
     TransformationBuffer.fromJavaList[Connection.KeyVal, org.jsoup.Connection.KeyVal](
       delegate.formData(),
       Transformer(_.delegate, Connection.KeyVal.apply))
}

object FormElement {
  def apply(delegate: jn.FormElement): FormElement = if (delegate == null) null else new FormElement(delegate)
}