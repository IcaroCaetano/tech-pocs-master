package com.rbleggi.templaterenderer.core

import com.rbleggi.templaterenderer.renderers.{CSVRenderer, HTMLRenderer, PDFRenderer}
import org.scalatest.funsuite.AnyFunSuite

class RendererFactoryTest extends AnyFunSuite {

  test("should return HTMLRenderer for type html") {
    val renderer = RendererFactory.getRenderer("html")
    assert(renderer.isInstanceOf[HTMLRenderer])
  }

  test("should return CSVRenderer for type csv") {
    val renderer = RendererFactory.getRenderer("csv")
    assert(renderer.isInstanceOf[CSVRenderer])
  }

  test("should return PDFRenderer for type pdf") {
    val renderer = RendererFactory.getRenderer("pdf")
    assert(renderer.isInstanceOf[PDFRenderer])
  }

  test("should throw IllegalArgumentException for unknown format") {
    val exception = intercept[IllegalArgumentException] {
      RendererFactory.getRenderer("xml")
    }
    assert(exception.getMessage == "Unknown format: xml")
  }
}
