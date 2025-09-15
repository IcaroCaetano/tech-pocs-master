package com.rbleggi.templaterenderer.core

import com.rbleggi.templaterenderer.renderers.CSVRenderer
import com.rbleggi.templaterenderer.renderers.HTMLRenderer
import com.rbleggi.templaterenderer.renderers.PDFRenderer

object RendererFactory {
    fun getRenderer(type: String): TemplateRenderer {
        return when (type.lowercase()) {
            "html" -> HTMLRenderer()
            "csv" -> CSVRenderer()
            "pdf" -> PDFRenderer()
            else -> throw IllegalArgumentException("Unknown format: $type")
        }
    }
}