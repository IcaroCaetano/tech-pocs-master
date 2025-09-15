package com.rbleggi.templaterenderer.core

import com.rbleggi.templaterenderer.renderers.CSVRenderer
import com.rbleggi.templaterenderer.renderers.HTMLRenderer
import com.rbleggi.templaterenderer.renderers.PDFRenderer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RendererFactoryTest {

    @Test
    fun `should return HTMLRenderer for type html`() {
        val renderer: TemplateRenderer = RendererFactory.getRenderer("html")
        assertTrue(renderer is HTMLRenderer)
    }

    @Test
    fun `should return CSVRenderer for type csv`() {
        val renderer: TemplateRenderer = RendererFactory.getRenderer("csv")
        assertTrue(renderer is CSVRenderer)
    }

    @Test
    fun `should return PDFRenderer for type pdf`() {
        val renderer: TemplateRenderer = RendererFactory.getRenderer("pdf")
        assertTrue(renderer is PDFRenderer)
    }

    @Test
    fun `should throw exception for unknown format`() {
        val exception = assertThrows<IllegalArgumentException> {
            RendererFactory.getRenderer("xml")
        }
        assertEquals("Unknown format: xml", exception.message)
    }
}
