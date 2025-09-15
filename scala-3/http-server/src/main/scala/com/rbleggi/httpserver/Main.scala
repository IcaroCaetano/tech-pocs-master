package com.rbleggi.httpserver

import java.net.{ServerSocket, Socket}
import java.io.{BufferedReader, InputStreamReader, PrintWriter}

trait GetRouteHandler {
  def handle(path: String): Option[String]
}

class HelloHandler extends GetRouteHandler {
  override def handle(path: String): Option[String] =
    if (path == "/hello") Some("Hello, World!") else None
}

class PingHandler extends GetRouteHandler {
  override def handle(path: String): Option[String] =
    if (path == "/ping") Some("pong") else None
}

@main def run(): Unit = {
  val port = 8080
  val server = new ServerSocket(port)
  println(s"HTTP server running on http://localhost:$port ...")

  val handlers: List[GetRouteHandler] = List(new HelloHandler, new PingHandler)

  while (true) {
    val client = server.accept()
    new Thread(() => handleClient(client, handlers)).start()
  }
}

def handleClient(client: Socket, handlers: List[GetRouteHandler]): Unit = {
  val in = new BufferedReader(new InputStreamReader(client.getInputStream))
  val out = new PrintWriter(client.getOutputStream)
  try {
    val requestLine = in.readLine()
    if (requestLine != null && requestLine.startsWith("GET ")) {
      val path = requestLine.split(" ")(1)
      val response = handlers.view.flatMap(_.handle(path)).headOption.getOrElse("Not Found")
      val status = if (response == "Not Found") "404 Not Found" else "200 OK"
      out.println(s"HTTP/1.1 $status\r\nContent-Type: text/plain\r\n\r\n$response")
    } else {
      out.println("HTTP/1.1 400 Bad Request\r\nContent-Type: text/plain\r\n\r\nBad Request")
    }
    out.flush()
  } finally {
    client.close()
  }
}
