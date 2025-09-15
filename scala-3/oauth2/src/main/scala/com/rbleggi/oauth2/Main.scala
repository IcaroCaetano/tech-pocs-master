package com.rbleggi.oauth2

trait GrantHandler {
  def handle(request: Map[String, String]): Map[String, String]
}

class AuthorizationCodeHandler extends GrantHandler {
  def handle(request: Map[String, String]): Map[String, String] = {
    val code = "auth_code_123"
    Map("code" -> code)
  }
}

class ClientCredentialsHandler extends GrantHandler {
  def handle(request: Map[String, String]): Map[String, String] = {
    val token = "access_token_abc"
    Map("access_token" -> token, "token_type" -> "bearer")
  }
}

object GrantHandlerFactory {
  def getHandler(grantType: String): Option[GrantHandler] = grantType match {
    case "authorization_code" => Some(new AuthorizationCodeHandler)
    case "client_credentials" => Some(new ClientCredentialsHandler)
    case _ => None
  }
}

object OAuth2Server {
  def processRequest(params: Map[String, String]): Map[String, String] = {
    val grantType = params.getOrElse("grant_type", "")
    GrantHandlerFactory.getHandler(grantType)
      .map(_.handle(params))
      .getOrElse(Map("error" -> "unsupported_grant_type"))
  }
}

@main def run(): Unit = {
  val authCodeRequest = Map("grant_type" -> "authorization_code", "client_id" -> "abc", "redirect_uri" -> "http://localhost/cb")
  val clientCredsRequest = Map("grant_type" -> "client_credentials", "client_id" -> "abc", "client_secret" -> "xyz")
  val invalidRequest = Map("grant_type" -> "password")

  println("Authorization Code Grant Response: " + OAuth2Server.processRequest(authCodeRequest))
  println("Client Credentials Grant Response: " + OAuth2Server.processRequest(clientCredsRequest))
  println("Invalid Grant Response: " + OAuth2Server.processRequest(invalidRequest))
}
