package com.rbleggi.templaterenderer.renderers

import com.rbleggi.templaterenderer.core.TemplateRenderer

class CSVRenderer extends TemplateRenderer {
  def render(data: Map[String, String]): Array[Byte] =
    println("Generating CSV Template...")
    val csvContent = data.map { case (key, value) => s"$key,$value" }.mkString("\n")
    println("CSV Template Generated Successfully!")
    csvContent.getBytes("UTF-8")
}
