package parser

import model.{CsvParserErr}
import org.specs2.Specification


class CsvParserSpec extends Specification {

  def is = sequential ^
    s2"""
    CsvParser
    ======================

      csvStringsToAddressBooks
        Should return a map containing AddressBook if is there are no errors when parsing csv strings $csvStringsToAddressBooksTestOne
        Should return an error (CsvParserErr) if date could not be parse $csvStringsToAddressBooksTestTwo
        Should return an error (CsvParserErr) if not a sex string $csvStringsToAddressBooksTestThree
       """

  val csvStringOne = """Bill McKnight, Male, 16/03/77
                       |Paul Robinson, Male, 15/01/85
                       |Gemma Lane, Female, 20/11/91""".stripMargin

  val csvStringTwo = """Bill McKnight, Male, 16/gary""".stripMargin

  val csvStringThree = """Bill McKnight, Dog, 16/03/77""".stripMargin


  val csvStringFive = """""".stripMargin

  def csvStringsToAddressBooksTestOne = {
    val csvStringIterator = io.Source.fromString(csvStringOne).getLines()
    val response = CsvParser.csvStringsToAddressBooks(csvStringIterator)

    val result = response.map{a =>
      println(a)
      a.size}
    println(result)
    result must beEqualTo(Right(3))
  }

  def csvStringsToAddressBooksTestTwo = {
    val expectedResult = CsvParserErr("error when converting csv strings to addressBook Invalid format: \"16/gary\" is malformed at \"gary\"")
    val csvStringIterator = io.Source.fromString(csvStringTwo).getLines()
    val result = CsvParser.csvStringsToAddressBooks(csvStringIterator)
    result must beEqualTo(Left(expectedResult))
  }

  def csvStringsToAddressBooksTestThree = {
    val expectedResult = CsvParserErr("error when converting csv strings to addressBook unknown sex in csv  Dog")
    val csvStringIterator = io.Source.fromString(csvStringThree).getLines()
    val result = CsvParser.csvStringsToAddressBooks(csvStringIterator)
    result must beEqualTo(Left(expectedResult))
  }
}
