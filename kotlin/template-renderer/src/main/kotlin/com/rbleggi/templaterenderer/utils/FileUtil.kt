package com.rbleggi.templaterenderer.utils

import java.io.File
import java.io.FileOutputStream

object FileUtil {
    fun saveToFile(filename: String, content: ByteArray) {
        val file = File(filename)
        FileOutputStream(file).use { outputStream -> outputStream.write(content) }
        println("File saved to: $filename\n")
    }
}