package com.rbleggi.templaterenderer.core

import com.rbleggi.templaterenderer.renderers.{CSVRenderer, HTMLRenderer, PDFRenderer}

object RendererFactory {
  def getRenderer(format: String): TemplateRenderer = format.toLowerCase match
    case "html" => new HTMLRenderer()
    case "csv" => new CSVRenderer()
    case "pdf" => new PDFRenderer()
    case _ => throw new IllegalArgumentException(s"Unknown format: $format")
}
