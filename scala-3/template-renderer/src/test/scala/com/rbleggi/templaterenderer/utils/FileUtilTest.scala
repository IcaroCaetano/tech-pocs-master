package com.rbleggi.templaterenderer.utils

import org.scalatest.funsuite.AnyFunSuite

import java.io.File
import scala.io.Source

class FileUtilTest extends AnyFunSuite {

  test("should successfully write text content to a file") {
    val testFilePath = "src/test/resources/test_output.txt"
    val testContent = "This is a test content.".getBytes("UTF-8")

    FileUtil.saveToFile(testFilePath, testContent)

    val testFile = new File(testFilePath)
    assert(testFile.exists(), "File should be created")

    val fileContent = Source.fromFile(testFile).mkString
    assert(fileContent == "This is a test content.", "File content should match expected output")

    testFile.delete()
  }

}
