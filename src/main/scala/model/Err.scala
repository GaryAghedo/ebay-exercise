package model


sealed trait Err {
  def message: String
}
case class NotFoundErr(message: String) extends Err
case class BadRequestErr(message: String) extends Err
case class CsvParserErr(message: String) extends Err
