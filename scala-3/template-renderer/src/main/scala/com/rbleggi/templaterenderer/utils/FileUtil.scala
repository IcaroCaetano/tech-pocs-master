package com.rbleggi.templaterenderer.utils

import java.io.{File, FileOutputStream}

object FileUtil {
  def saveToFile(filename: String, content: Array[Byte]): Unit = 
    val fos = new FileOutputStream(new File(filename))
    fos.write(content)
    fos.close()
    println(s"File saved to: $filename\n")
}
