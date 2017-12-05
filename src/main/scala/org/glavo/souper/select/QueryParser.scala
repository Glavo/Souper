package org.glavo.souper.select

trait QueryParser {
  def parse(query: String): Evaluator
}

object QueryParser {

}

object CssQueryParser extends QueryParser  {
  override def parse(query: String): Evaluator =
    org.jsoup.select.QueryParser.parse(query).asSouper
}
