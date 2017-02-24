package model

import org.joda.time.DateTime

sealed trait Sex
case object Male extends Sex
case object Female extends Sex
case class AddressBook(fullName: String, sex: Sex, dateOfBirth: DateTime)
