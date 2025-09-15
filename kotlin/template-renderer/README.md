# Template Renderer Project

## Overview

This project follows an **Object-Oriented Design (OOP)** approach to generate templates in **HTML, CSV, and PDF** formats. It uses:

- **Kotlin** → A concise, modern JVM-based language.
- **iText** → A library for PDF generation.

## Features

- **HTML Rendering** → Generates HTML files.  
- **CSV Rendering** → Generates CSV files.  
- **PDF Rendering** → Uses iText for generating PDF files.  
- **Factory Pattern** → Dynamically selects the correct renderer.  
- **Encapsulation & Polymorphism** → Each renderer extends the base `TemplateRenderer` class.  

---

## Architecture Diagram

The following **Mermaid UML Diagram** illustrates the **Object-Oriented structure** of this project:

```mermaid
classDiagram
   class TemplateRenderer {
      <<abstract>>
      +render(data: Map<String, String>): ByteArray
   }

   class HTMLRenderer {
      +render(data: Map<String, String>): ByteArray
   }

   class CSVRenderer {
      +render(data: Map<String, String>): ByteArray
   }

   class PDFRenderer {
      +render(data: Map<String, String>): ByteArray
   }

   class RendererFactory {
      +getRenderer(type: String): TemplateRenderer
   }

   class FileUtil {
      +saveToFile(filename: String, content: ByteArray)
   }

   class Main {
      +main(args: Array<String>)
   }

   TemplateRenderer <|-- HTMLRenderer
   TemplateRenderer <|-- CSVRenderer
   TemplateRenderer <|-- PDFRenderer
   RendererFactory --> TemplateRenderer
   Main --> RendererFactory
   Main --> FileUtil
```

### **Explanation**
- **TemplateRenderer** (Abstract Class) → Base class for all renderers.
- **HTMLRenderer, CSVRenderer, PDFRenderer** → Implement `render()` differently based on the file format.
- **RendererFactory** → Implements the **Factory Pattern** to return the correct renderer.

---

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/rbleggi/tech-pocs.git
   cd kotlin/template-renderer
   ```

2. **Compiling & Running**:
   ```bash
   ./gradlew build run
   ```

3. **Tests**:
   ```sh
   ./gradlew test
   ```