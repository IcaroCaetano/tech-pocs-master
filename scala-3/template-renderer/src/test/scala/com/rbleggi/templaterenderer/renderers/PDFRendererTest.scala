package com.rbleggi.templaterenderer.renderers

import org.scalatest.funsuite.AnyFunSuite

class PDFRendererTest extends AnyFunSuite {

  test("should generate valid PDF content") {
    val pdfBytes = new PDFRenderer().render(Map("title" -> "PDF Test", "content" -> "Testing PDF generation"))
    assert(pdfBytes.nonEmpty, "Generated PDF content should not be empty")
  }
}
