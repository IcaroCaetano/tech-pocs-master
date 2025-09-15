package com.rbleggi.templaterenderer.utils

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class FileUtilTest {

    @Test
    fun `should write text content to a file`() {
        val testFilePath = "src/test/resources/test_output.txt"

        FileUtil.saveToFile(testFilePath, "test content.".toByteArray())

        val testFile = File(testFilePath)
        assertTrue(testFile.exists(), "File should be created")
        assertTrue(testFile.readText() == "test content.", "File content should match")

        testFile.delete()
    }

}