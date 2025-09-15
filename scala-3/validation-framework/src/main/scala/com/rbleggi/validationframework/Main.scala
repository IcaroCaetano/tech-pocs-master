package com.rbleggi.validationframework

trait Validator[T] {
  def validate(value: T): List[String]
}

case class User(name: String, email: String)

object Validator {
  def apply[T](implicit v: Validator[T]): Validator[T] = v

  val notNullString: Validator[String] = new Validator[String] {
    def validate(value: String): List[String] =
      if (value != null) Nil else List("Value cannot be null.")
  }

  def minLength(min: Int): Validator[String] = new Validator[String] {
    def validate(value: String): List[String] =
      if (value != null && value.length >= min) Nil else List(s"Value does not meet minimum length $min.")
  }

  def all[T](validators: Validator[T]*): Validator[T] = new Validator[T] {
    def validate(value: T): List[String] = validators.toList.flatMap(_.validate(value))
  }
}

given userValidator: Validator[User] = new Validator[User] {
  private val nameValidator = Validator.all(Validator.notNullString, Validator.minLength(3))
  private val emailValidator = Validator.notNullString
  def validate(user: User): List[String] =
    nameValidator.validate(user.name).map("name: " + _) ++
    emailValidator.validate(user.email).map("email: " + _)
}

@main def runValidation(): Unit =
  val user = User(null, "a@b.com")
  val errors = Validator[User].validate(user)
  println(errors)
