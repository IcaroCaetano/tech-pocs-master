package com.rbleggi.yamlcodegenerator

@main def runYamlCodeGenerator(): Unit = {
  val yamlContent =
    """
Person:
  name: String
  age: Int
  address: Address
  phones: List[String]

Address:
  street: String
  number: Int
  city: String
  state: String

Company:
  name: String
  cnpj: String
  employees: List[Person]
""".stripMargin

  val generator: GeneratorStrategy = new CaseClassGenerator()
  val generatedCode = generator.generate(yamlContent)

  println("Generated code:\n" + generatedCode)
}

trait GeneratorStrategy {
  def generate(yaml: String): String
}

class CaseClassGenerator extends GeneratorStrategy {
  override def generate(yaml: String): String = {
    val lines = yaml.split("\n").map(_.trim).filter(_.nonEmpty)
    if (lines.isEmpty) return "// empty YAML"

    val className = lines.head.replace(":", "")
    val fields = lines.tail.map { line =>
      val Array(name, tpe) = line.split(":").map(_.trim)
      s"  $name: $tpe"
    }
    s"case class $className(\n${fields.mkString(",\n")}\n)"
  }
}
