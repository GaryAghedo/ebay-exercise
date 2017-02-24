package repository

import model.{AddressBook, Err, NotFoundErr}
import parser.CsvParser

case class AddressBookRepository(url: String) extends Repository {
  val addressBookRepo: Either[Err, Map[String, AddressBook]] = {
    CsvParser.addressBookOps(url)
  }

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
