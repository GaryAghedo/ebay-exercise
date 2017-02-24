package helper

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter, DateTimeFormatterBuilder, DateTimeParser}
object DateHelpers {

  lazy val DATE_PARSER: DateTimeFormatter = {
    val parsers: Array[DateTimeParser] = Array[DateTimeParser](
      DateTimeFormat.forPattern("dd/MM/yy").getParser()
    )
    new DateTimeFormatterBuilder().append(null, parsers).toFormatter()
  }

  object JodaOrdering {
    implicit def orderDateTime: Ordering[DateTime] = Ordering.fromLessThan(_ isBefore _)
  }

}
