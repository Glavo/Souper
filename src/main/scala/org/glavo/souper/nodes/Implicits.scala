package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

import scala.language.implicitConversions

trait Implicits {
  implicit final def nodeWrapper(node: jn.Node): Node = node match {
    case null => null
    case elem: jn.Element => elementWrapper(elem)

    //LeafNode
    case comment: jn.Comment => Comment(comment)
    case data: jn.DataNode => DataNode(data)
    case docType: jn.DocumentType => DocumentType(docType)
    case text: jn.TextNode => TextNode(text)
    case xml: jn.XmlDeclaration => XmlDeclaration(xml)
    case _ => new Node {
      override val delegate: jn.Node = node
    }
  }

  implicit final def elementWrapper(elem: jn.Element): Element = elem match {
    case null => null
    case doc: jn.Document => Document(doc)
    case form: jn.FormElement => FormElement(form)
    case pseudo: jn.PseudoTextElement => PseudoTextElement(pseudo)
    case _ => new Element(elem)
  }

  implicit final def documentWrapper(doc: jn.Document): Document =
    if (doc != null) new Document(doc) else null

  implicit final def formElementWrapper(elem: jn.FormElement): FormElement =
    if (elem != null) new FormElement(elem) else null

  implicit final def pseudoTextElementWrapper(elem: jn.PseudoTextElement): PseudoTextElement =
    if (elem != null) new PseudoTextElement(elem) else null

  implicit final def commentWrapper(comment: jn.Comment): Comment =
    if (comment != null) new Comment(comment) else null

  implicit final def dataNodeWrapper(node: jn.DataNode): DataNode =
    if (node != null) new DataNode(node) else null

  implicit final def documentTypeWrapper(docType: jn.DocumentType): DocumentType =
    if (docType != null) new DocumentType(docType) else null

  implicit final def textNodeWrapper(node: jn.TextNode): TextNode =
    if (node != null) new TextNode(node) else null

  implicit final def xmlDeclarationWrapper(xmlDeclaration: jn.XmlDeclaration): XmlDeclaration =
    if(xmlDeclaration != null) new XmlDeclaration(xmlDeclaration) else null
}

object Implicits extends Implicits
