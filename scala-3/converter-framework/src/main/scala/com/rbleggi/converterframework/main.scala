package com.rbleggi.converterframework

trait Converter[A, B] {
  def convert(a: A): B
}

object Converter {
  def apply[A, B](f: A => B): Converter[A, B] = new Converter[A, B] {
    def convert(a: A): B = f(a)
  }
}

@main def runRestaurantQueue(): Unit =
  val queue = KitchenQueue()

case class Address(street: String, city: String, zip: String)
case class Person(name: String, age: Int, address: Address)
case class PersonDTO(fullName: String, age: Int, city: String)

val addressToString: Converter[Address, String] = Converter(addr => s"${addr.street}, ${addr.city}, ${addr.zip}")
val personToDTO: Converter[Person, PersonDTO] = Converter(p => PersonDTO(p.name, p.age, p.address.city))
val personToString: Converter[Person, String] = Converter(p => s"${p.name} (${p.age}), ${addressToString.convert(p.address)}")

@main def runConverterExamples(): Unit =
  val address = Address("123 Main St", "Springfield", "12345")
  val person = Person("John Doe", 30, address)

  println("Address as String: " + addressToString.convert(address))
  println("Person as DTO: " + personToDTO.convert(person))
  println("Person as String: " + personToString.convert(person))
