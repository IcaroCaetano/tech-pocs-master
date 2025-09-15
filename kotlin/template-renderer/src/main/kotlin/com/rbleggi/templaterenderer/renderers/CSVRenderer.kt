package com.rbleggi.templaterenderer.renderers

import com.rbleggi.templaterenderer.core.TemplateRenderer

class CSVRenderer : TemplateRenderer() {
    override fun render(data: Map<String, String>): ByteArray {
        println("Generating CSV Template...")

        val csvContent = data.entries.joinToString("\n") { "${it.key},${it.value}" }

        println("CSV Template Generated Successfully!")
        return csvContent.toByteArray()
    }
}