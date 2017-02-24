package parser
import java.nio.file.Paths

import model._
import helper.DateHelpers.DATE_PARSER
object CsvParser {

  def parseCsvLine(path: String): Either[Err, Iterator[String]] =
    try {
      Right(io.Source.fromFile(Paths.get(this.getClass.getResource(path).toURI).toString).getLines())
    } catch {
      case ex: Exception => Left(CsvParserErr(s"error when reading file: ${ex.getMessage}"))
    }

  def csvStringsToAddressBooks(lines: Iterator[String]): Either[Err, Map[String, AddressBook]]= {
    try {
      Right {
        val addressBooks: Iterator[AddressBook] = lines.map { line => line.split(",") }.map { cols =>
          AddressBook(
            cols(0).trim,
            cols(1).toLowerCase.trim match {
              case "female" => Female
              case "male" => Male
              case _ => throw new IllegalArgumentException(s"unknown sex in csv ${cols(1)}")
            },
            DATE_PARSER.parseDateTime(cols(2).trim)
          )
        }
        addressBooks.map(addressBook => addressBook.fullName -> addressBook).toMap
      }
    }
    catch {
      case ex: Exception => Left(CsvParserErr(s"error when converting csv strings to addressBook ${ex.getMessage}"))
    }
  }

  def addressBookOps(path: String): Either[Err, Map[String, AddressBook]] =
    parseCsvLine(path).flatMap{ lines => csvStringsToAddressBooks(lines)}

}
