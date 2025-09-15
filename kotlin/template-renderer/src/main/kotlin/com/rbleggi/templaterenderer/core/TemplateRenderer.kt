package com.rbleggi.templaterenderer.core

abstract class TemplateRenderer {
    abstract fun render(data: Map<String, String>): ByteArray
}