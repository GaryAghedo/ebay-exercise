package repository

import model.{AddressBook, Err}

trait Repository {
  val addressBookRepo : Either[Err, Map[String, AddressBook]]
  def query(id: String): Either[Err, AddressBook]
  def getAll: Either[Err, List[AddressBook]]
}



