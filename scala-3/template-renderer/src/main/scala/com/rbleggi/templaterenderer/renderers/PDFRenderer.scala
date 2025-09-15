package com.rbleggi.templaterenderer.renderers

import com.rbleggi.templaterenderer.core.TemplateRenderer
import com.itextpdf.kernel.pdf.{PdfDocument, PdfWriter}
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.ByteArrayOutputStream

class PDFRenderer extends TemplateRenderer {
  def render(data: Map[String, String]): Array[Byte] =
    println("Generating PDF Template...")
    val outputStream = new ByteArrayOutputStream()
    val document = new Document(new PdfDocument(new PdfWriter(outputStream)))

    document.add(new Paragraph(s"Title: ${data("title")}"))
    document.add(new Paragraph(s"Content: ${data("content")}"))

    document.close()
    println("PDF Template Generated Successfully!")
    outputStream.toByteArray
}
