package service

import model.{AddressBook, BadRequestErr, Err, NotFoundErr}
import org.joda.time.Days
import reader.Reader
import repository.Repository
import helper.DateHelpers.JodaOrdering._


case class AddressBookService(){

  val findNumberOfMales: Reader[Repository, Either[Err,Int]] = Reader{ repo: Repository =>
    repo.getAll.map(addressBooks => addressBooks.map(_.sex).size)
  }

  def findOldestPerson: Reader[Repository, Either[Err, AddressBook]] = Reader{ repo: Repository =>
    repo.getAll.flatMap{addressBooks =>
      addressBooks.sortBy(_.dateOfBirth).headOption.map{addressbook =>
        Right(addressbook)
      }.getOrElse(Left(NotFoundErr("addressBook is empty")))}
  }

  def FindDifferentInAge(firstPersonName: String, secondPersonName: String): Reader[Repository, Either[Err, Int]] = Reader { repo: Repository =>

    repo.query(firstPersonName).flatMap{person1 =>
       repo.query(secondPersonName).flatMap{ person2 =>
         if(person2.dateOfBirth.isBefore(person1.dateOfBirth)){
           Left(BadRequestErr(s"$secondPersonName is old than $firstPersonName"))
         } else {
           Right(Days.daysBetween(person1.dateOfBirth, person2.dateOfBirth).getDays)
         }
       }
    }
  }

}
