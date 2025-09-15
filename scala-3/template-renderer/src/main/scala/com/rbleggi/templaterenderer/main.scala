package com.rbleggi.templaterenderer

import com.rbleggi.templaterenderer.core.RendererFactory
import com.rbleggi.templaterenderer.utils.FileUtil

@main def run(): Unit = {
  List("html", "csv", "pdf").foreach { format =>
    val data = Map("title" -> "Example Title", "content" -> "This is an example content for testing.")
    FileUtil.saveToFile(s"src/main/resources/output.$format", RendererFactory.getRenderer(format).render(data))
  }
}
