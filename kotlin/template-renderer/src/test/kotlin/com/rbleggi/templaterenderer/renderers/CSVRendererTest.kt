package com.rbleggi.templaterenderer.renderers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CSVRendererTest {

    @Test
    fun `should generate valid CSV output`() {
        assertEquals("Name,Roger\nAge,30", String(CSVRenderer().render(mapOf("Name" to "Roger", "Age" to "30"))))
    }
}
