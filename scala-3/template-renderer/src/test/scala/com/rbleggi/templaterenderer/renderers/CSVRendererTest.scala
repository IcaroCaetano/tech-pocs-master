package com.rbleggi.templaterenderer.renderers

import org.scalatest.funsuite.AnyFunSuite

class CSVRendererTest extends AnyFunSuite {

  test("should generate valid CSV output") {
    val output = new CSVRenderer().render(Map("Name" -> "Roger", "Age" -> "30"))
    assert(new String(output) == "Name,Roger\nAge,30")
  }

}
