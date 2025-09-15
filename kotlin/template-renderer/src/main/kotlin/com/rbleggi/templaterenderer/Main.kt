package com.rbleggi.templaterenderer

import com.rbleggi.templaterenderer.core.RendererFactory
import com.rbleggi.templaterenderer.utils.FileUtil

fun main() {
    listOf("html", "csv", "pdf").forEach { type ->
        val data = mapOf("title" to "Example Title", "content" to "Example content")
        FileUtil.saveToFile("src/main/resources/output.$type", RendererFactory.getRenderer(type).render(data))
    }
}
