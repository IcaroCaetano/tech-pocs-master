package com.rbleggi.templaterenderer.renderers

import com.rbleggi.templaterenderer.core.TemplateRenderer

class HTMLRenderer extends TemplateRenderer {
  override def render(data: Map[String, String]): Array[Byte] =
    println("Generating HTML Template...")
    val htmlContent =
      s"""
         |<html>
         |<head><title>${data("title")}</title></head>
         |<body>
         |<h1>${data("title")}</h1>
         |<p>${data("content")}</p>
         |</body>
         |</html>
         |""".stripMargin
    println("HTML Template Generated Successfully!")
    htmlContent.getBytes()
}
