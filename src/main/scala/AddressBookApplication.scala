import parser.CsvParser
import repository.AddressBookRepository
import service.AddressBookService

object AddressBookApplication extends App {

  val addressBookService = AddressBookService()
  val path = "/data/addressBook.csv"
  val repo = AddressBookRepository(path)

  val numberOfMales =  addressBookService.findNumberOfMales.run(repo)
  println(s"number of male in address book $numberOfMales")

  val oldestPerson = addressBookService.findOldestPerson.run(repo)
  oldestPerson match {
    case Right(person) => println(s"the oldest person in the address book is ${person.fullName}")
    case Left(err) => println(err.message)
  }

  val differentInAge = addressBookService.FindDifferentInAge("Bill McKnight","Paul Robinson").run(repo)
  differentInAge match{
    case Right(days) => println(s"Bill is $days older than paul")
    case Left(err) => println(err.message)
  }
}
