package org.glavo.souper.select

import org.glavo.souper.{SouperDelegate, nodes}
import org.glavo.souper.nodes._
import org.jsoup.{nodes => jn, select => js}

trait Evaluator {

  final class AsJsoup extends org.jsoup.select.Evaluator {
    def asSouper: Evaluator = Evaluator.this

    override def matches(root: jn.Element, element: jn.Element): Boolean = {
      Evaluator.this.matches(nodes.Implicits.elementWrapper(root), nodes.Implicits.elementWrapper(element))
    }

    override def toString: String = Evaluator.this.toString
  }

  def matches(root: Element, element: Element): Boolean

  def delegate: js.Evaluator = new AsJsoup()
}

//noinspection NotImplementedCode
trait WrappedEvaluator[+T <: js.Evaluator] extends Evaluator with SouperDelegate[T] {
  override def matches(root: Element, element: Element): Boolean = delegate.matches(root.delegate, element.delegate)

  override def delegate: T = ???
}

object Evaluator {

  private[select] final class DefaultEvaluator(override val delegate: js.Evaluator) extends WrappedEvaluator[js.Evaluator]

  def apply(evaluator: js.Evaluator): Evaluator = evaluator match {
    case null => null
    case e: js.Evaluator.Tag => Tag(e)
    case e: js.Evaluator.TagEndsWith => TagEndsWith(e)
    case e: js.Evaluator.Id => Id(e)
    //todo
    case e: Evaluator#AsJsoup => e.asSouper
    case _ => new DefaultEvaluator(evaluator)
  }

  final class Tag(override val delegate: js.Evaluator.Tag) extends WrappedEvaluator[js.Evaluator.Tag]

  object Tag {
    def apply(delegate: js.Evaluator.Tag): Tag = if (delegate != null) new Tag(delegate) else null

    def apply(tagName: String): Tag = new Tag(new js.Evaluator.Tag(tagName))
  }


  final class TagEndsWith(override val delegate: js.Evaluator.TagEndsWith)
    extends WrappedEvaluator[js.Evaluator.TagEndsWith]

  object TagEndsWith {
    def apply(delegate: js.Evaluator.TagEndsWith): TagEndsWith = if (delegate != null) new TagEndsWith(delegate) else null

    def apply(tagName: String): TagEndsWith = new TagEndsWith(new js.Evaluator.TagEndsWith(tagName))
  }

  final class Id(override val delegate: js.Evaluator.Id)
    extends WrappedEvaluator[js.Evaluator.Id]

  object Id {
    def apply(delegate: js.Evaluator.Id): Id = if (delegate != null) new Id(delegate) else null

    def apply(id: String): Id = new Id(new js.Evaluator.Id(id))
  }

  final class Class(override val delegate: js.Evaluator.Class)
    extends WrappedEvaluator[js.Evaluator.Class]

  object Class {
    def apply(delegate: js.Evaluator.Class): Class = if (delegate != null) new Class(delegate) else null

    def apply(className: String): Class = new Class(new js.Evaluator.Class(className))
  }

  final class Attribute(override val delegate: js.Evaluator.Attribute)
    extends WrappedEvaluator[js.Evaluator.Attribute]

  object Attribute {
    def apply(delegate: js.Evaluator.Attribute): Attribute = if (delegate != null) new Attribute(delegate) else null

    def apply(key: String): Attribute = new Attribute(new js.Evaluator.Attribute(key))
  }

  final class AttributeStarting(override val delegate: js.Evaluator.AttributeStarting)
    extends WrappedEvaluator[js.Evaluator.AttributeStarting]

  object AttributeStarting {
    def apply(delegate: js.Evaluator.AttributeStarting): AttributeStarting =
      if (delegate != null) new AttributeStarting(delegate) else null

    def apply(keyPrefix: String): AttributeStarting = new AttributeStarting(new js.Evaluator.AttributeStarting(keyPrefix))
  }

  abstract class AttributeKeyPair extends Evaluator
  //todo
}
