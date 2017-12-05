package org.glavo.souper

import org.jsoup.{nodes => jn}

package object nodes {

  // RichNode

  implicit final class RichNode(val node: jn.Node) extends AnyVal {
    @inline
    def asSouper: Node = Node(node)
  }

  // RichElement

  implicit final class RichElement(val node: jn.Element) extends AnyVal {
    @inline
    def asSouper: Element = Element(node)
  }

  implicit final class RichDocument(val node: jn.Document) extends AnyVal {
    @inline
    def asSouper: Document = Document(node)
  }

  implicit final class RichFormElement(val node: jn.FormElement) extends AnyVal {
    @inline
    def asSouper: FormElement = FormElement(node)
  }

  implicit final class RichPseudoTextElement(val node: jn.PseudoTextElement) extends AnyVal {
    @inline
    def asSouper: PseudoTextElement = PseudoTextElement(node)
  }

  // RichLeafNode

  implicit final class RichComment(val node: jn.Comment) extends AnyVal {
    @inline
    def asSouper: Comment = Comment(node)
  }

  implicit final class RichDataNode(val node: jn.DataNode) extends AnyVal {
    @inline
    def asSouper: DataNode = DataNode(node)
  }

  implicit final class RichDocumentType(val node: jn.DocumentType) extends AnyVal {
    @inline
    def asSouper: DocumentType = DocumentType(node)
  }

  implicit final class RichTextNode(val node: jn.TextNode) extends AnyVal {
    @inline
    def asSouper: TextNode = TextNode(node)
  }

  implicit final class RichXmlDeclaration(val node: jn.XmlDeclaration) extends AnyVal {
    @inline
    def asSouper: XmlDeclaration = XmlDeclaration(node)
  }

}
