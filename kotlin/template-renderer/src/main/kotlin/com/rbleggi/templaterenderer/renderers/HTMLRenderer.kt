package com.rbleggi.templaterenderer.renderers

import com.rbleggi.templaterenderer.core.TemplateRenderer

class HTMLRenderer : TemplateRenderer() {
    override fun render(data: Map<String, String>): ByteArray {
        println("Generating HTML Template...")

        val htmlContent = """
            <html>
            <head>
                <title>${data["title"]}</title>
            </head>
            <body>
                <h1>${data["title"]}</h1>
                <p>${data["content"]}</p>
            </body>
            </html>
        """.trimIndent()

        println("HTML Template Generated Successfully!")

        return htmlContent.toByteArray()
    }
}