package com.rbleggi.templaterenderer.renderers

import org.scalatest.funsuite.AnyFunSuite

class HTMLRendererTest extends AnyFunSuite {
  test("should generate valid HTML output") {
    val output = new HTMLRenderer().render(Map("title" -> "Test Title", "content" -> "Test Content"))
    assert(new String(output) ==
      """
        |<html>
        |<head><title>Test Title</title></head>
        |<body>
        |<h1>Test Title</h1>
        |<p>Test Content</p>
        |</body>
        |</html>
        |""".stripMargin)
  }
}
