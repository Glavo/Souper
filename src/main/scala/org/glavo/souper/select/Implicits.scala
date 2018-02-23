package org.glavo.souper.select

import org.jsoup.{select => js}

import scala.language.implicitConversions

trait Implicits {
  implicit final def elementsWrapper(elements: js.Elements): Elements = Elements(elements)
  implicit final def evaluatorWrapper(evaluator: js.Evaluator): Evaluator = Evaluator(evaluator)
}

object Implicits
