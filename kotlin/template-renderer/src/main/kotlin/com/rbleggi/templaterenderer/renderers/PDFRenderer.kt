package com.rbleggi.templaterenderer.renderers

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.rbleggi.templaterenderer.core.TemplateRenderer
import java.io.ByteArrayOutputStream

class PDFRenderer : TemplateRenderer() {
    override fun render(data: Map<String, String>): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val document = Document(PdfDocument(PdfWriter(outputStream)))

        println("Generating PDF Template...")

        document.add(Paragraph("Title: ${data["title"]}"))
        document.add(Paragraph("Content: ${data["content"]}"))

        document.close()

        println("PDF Template Generated Successfully!")

        return outputStream.toByteArray()
    }
}