package org.glavo.souper.nodes

import org.jsoup.{nodes => jn}

import scala.language.implicitConversions

trait Implicits {
  implicit final def nodeWrapper(node: jn.Node): Node = Node(node)

  implicit final def elementWrapper(elem: jn.Element): Element = Element(elem)

  implicit final def documentWrapper(doc: jn.Document): Document = Document(doc)

  implicit final def formElementWrapper(elem: jn.FormElement): FormElement = FormElement(elem)

  implicit final def pseudoTextElementWrapper(elem: jn.PseudoTextElement): PseudoTextElement = PseudoTextElement(elem)

  implicit final def commentWrapper(comment: jn.Comment): Comment = Comment(comment)

  implicit final def dataNodeWrapper(node: jn.DataNode): DataNode = DataNode(node)

  implicit final def documentTypeWrapper(docType: jn.DocumentType): DocumentType = DocumentType(docType)

  implicit final def textNodeWrapper(node: jn.TextNode): TextNode = TextNode(node)

  implicit final def xmlDeclarationWrapper(xmlDeclaration: jn.XmlDeclaration): XmlDeclaration = XmlDeclaration(xmlDeclaration)
}

object Implicits extends Implicits
