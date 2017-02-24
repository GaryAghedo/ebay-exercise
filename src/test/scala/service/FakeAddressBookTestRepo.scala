package service

import model.{AddressBook, Err, NotFoundErr}
import repository.Repository

case class FakeAddressBookTestRepo(response: Either[Err, Map[String, AddressBook]]) extends Repository {

  val addressBookRepo : Either[Err, Map[String, AddressBook]] = response
  def query(name: String): Either[Err, AddressBook] = {
    addressBookRepo.flatMap { addressBook => addressBook.get(name) match {
      case Some(result) => Right(result)
      case None => Left(NotFoundErr("item with name $name not found"))
    }
    }
  }
  def getAll: Either[Err, List[AddressBook]] = {
    addressBookRepo.map { addressBook => addressBook.values.toList }
  }
}
