package com.rbleggi.templaterenderer.core

abstract class TemplateRenderer {
  def render(data: Map[String, String]): Array[Byte]
}
