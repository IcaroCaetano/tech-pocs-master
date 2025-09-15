package com.rbleggi.templaterenderer.renderers

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PDFRendererTest {

    @Test
    fun `should generate valid PDF content`() {
        val pdfBytes = PDFRenderer().render(mapOf("title" to "PDF Test", "content" to "Testing PDF generation"))
        assertTrue(pdfBytes.isNotEmpty(), "Generated PDF content should not be empty")
    }
}
