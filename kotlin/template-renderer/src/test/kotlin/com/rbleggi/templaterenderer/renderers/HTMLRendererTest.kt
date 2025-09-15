package com.rbleggi.templaterenderer.renderers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HTMLRendererTest {

    @Test
    fun `should generate valid HTML output`() {
        assertEquals(
            """
            <html>
            <head>
                <title>Test Title</title>
            </head>
            <body>
                <h1>Test Title</h1>
                <p>Test Content</p>
            </body>
            </html>
        """.trimIndent(), String(HTMLRenderer().render(mapOf("title" to "Test Title", "content" to "Test Content")))
        )
    }
}
