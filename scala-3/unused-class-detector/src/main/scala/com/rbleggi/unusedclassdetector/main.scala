package com.rbleggi.unusedclassdetector

import scala.io.Source
import scala.util.matching.Regex

trait ASTNode
case class ClassDecl(name: String) extends ASTNode
case class ClassUsage(name: String) extends ASTNode
case class SourceFile(content: String) extends ASTNode

trait ASTVisitor {
  def visit(source: SourceFile): AnalysisResult
}

case class AnalysisResult(unusedClasses: List[String])

class UnusedClassVisitor extends ASTVisitor {
  val classDeclPattern: Regex = "class\\s+(\\w+)".r
  val classUsagePattern: Regex = "new\\s+(\\w+)".r
  def visit(source: SourceFile): AnalysisResult = {
    val decls = classDeclPattern.findAllMatchIn(source.content).map(_.group(1)).toSet
    val usages = classUsagePattern.findAllMatchIn(source.content).map(_.group(1)).toSet
    val excluded = Set("ClassDecl", "ClassUsage", "SourceFile", "AnalysisResult")
    val unused = decls.diff(usages).filterNot(name => excluded.contains(name) || name.endsWith("Decl") || name.endsWith("Usage") || name.endsWith("Result") || name.endsWith("File")).toList
    AnalysisResult(unused)
  }
}

@main def run(): Unit = {
  val path = "src/main/scala/com/rbleggi/unusedclassdetector/main.scala"
  val content = Source.fromFile(path).mkString
  val sourceFile = SourceFile(content)
  val visitor = new UnusedClassVisitor
  val result = visitor.visit(sourceFile)
  println("Unused classes:")
  result.unusedClasses.foreach(println)
}

class UnusedExampleClass {
  def hello(): String = "Hello, I am not used!"
}
