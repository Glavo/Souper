package org.glavo.souper.nodes

import org.glavo.souper.Connection
import org.jsoup.{nodes => js}

import scala.collection.JavaConverters._
import scala.collection.mutable

class FormElement(override val asJsoup: js.FormElement) extends Element(asJsoup) {
  //todo def elements: Elements

  def addElement(element: Element): FormElement.this.type = {
    asJsoup.addElement(element.asJsoup)
    this
  }

  def submit: Connection = asJsoup.submit()

  def formData: mutable.Buffer[Connection.KeyVal] = asJsoup.formData().asScala
}

object FormElement {
  def apply(asJsoup: js.FormElement): FormElement = if (asJsoup == null) null else new FormElement(asJsoup)
}