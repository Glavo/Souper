package org.glavo.souper.select

import org.glavo.souper.Implicits._

trait QueryParser {
  def parse(query: String): Evaluator
}

object QueryParser {

}

object CssQueryParser extends QueryParser  {
  override def parse(query: String): Evaluator =
    org.jsoup.select.QueryParser.parse(query)
}
