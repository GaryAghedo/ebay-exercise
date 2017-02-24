package service

import model._
import org.joda.time.DateTime
import org.specs2.Specification


class AddressBookServiceSpec extends Specification {

  def is = sequential ^
    s2"""
    AddressBookService
    ======================

      findNumberOfMales
        Should return correct number when csv parsing is successfully $findNumberOfMalesTestOne
      findOldestPerson
        Should return correct person when csv parsing is successfully $findOldestPersonTestOne
        Should return an error (NotFoundErr) is csv is empty $findOldestPersonTestTwo
      FindDifferentInAge
        Should return correct days when csv parsing is successfully $findDifferentInAgeTestOne
        Should return an error (BadRequestErr) when first person ago is less than second Age $findDifferentInAgeTestTwo
  """

  val response = Map("Bill McKnight" -> AddressBook("Bill McKnight",Male, DateTime.parse("1977-03-16T00:00:00.000Z")),
    "Paul Robinson" -> AddressBook("Paul Robinson",Male,DateTime.parse("1985-01-15T00:00:00.000Z")),
    "Gemma Lane" -> AddressBook("Gemma Lane",Female,DateTime.parse("1991-11-20T00:00:00.000Z")))

  val response2 = Map[String, AddressBook]()
  val addressBookService = AddressBookService()
  def findNumberOfMalesTestOne = {
    val repo = FakeAddressBookTestRepo(Right(response))
    val result = addressBookService.findNumberOfMales.run(repo)
    result must beEqualTo(Right(3))
  }

  def findOldestPersonTestOne = {
    val repo = FakeAddressBookTestRepo(Right(response))
    val result = addressBookService.findOldestPerson.run(repo)
    result must beEqualTo(Right(AddressBook("Bill McKnight",Male, DateTime.parse("1977-03-16T00:00:00.000Z"))))
  }

  def findOldestPersonTestTwo = {
    val repo = FakeAddressBookTestRepo(Right(response2))
    val result = addressBookService.findOldestPerson.run(repo)
    result must beEqualTo(Left(NotFoundErr("addressBook is empty")))
  }

  def findDifferentInAgeTestOne = {
    val repo = FakeAddressBookTestRepo(Right(response))
    val result = addressBookService.FindDifferentInAge("Bill McKnight", "Paul Robinson").run(repo)
    result must beEqualTo(Right(2862))
  }

  def findDifferentInAgeTestTwo = {
    val repo = FakeAddressBookTestRepo(Right(response))
    val result = addressBookService.FindDifferentInAge("Paul Robinson", "Bill McKnight").run(repo)
    result must beEqualTo(Left(BadRequestErr("Bill McKnight is old than Paul Robinson")))
  }



}

