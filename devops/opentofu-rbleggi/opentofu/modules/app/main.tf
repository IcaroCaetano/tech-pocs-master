# App deployment module for OpenTofu

variable "namespace" {
  description = "The namespace where the application will be deployed"
  type        = string
  default     = "applications"
}

variable "app_name" {
  description = "The name of the application"
  type        = string
  default     = "java-app"
}

variable "app_image" {
  description = "The Docker image of the application"
  type        = string
  default     = "tech-pocs/java-devops-app"
}

variable "app_tag" {
  description = "The Docker image tag of the application"
  type        = string
  default     = "latest"
}

variable "prometheus_enabled" {
  description = "Enable Prometheus integration"
  type        = bool
  default     = true
}

# Deploy the application using Helm
resource "helm_release" "app" {
  name       = var.app_name
  chart      = "${path.root}/../helm/sample-app"
  namespace  = var.namespace
  create_namespace = true

  values = [
    file("${path.module}/values.yaml")
  ]

  # Override values.yaml with variables if provided
  set {
    name  = "image.repository"
    value = var.app_image
  }

  set {
    name  = "image.tag"
    value = var.app_tag
  }

  set {
    name  = "prometheus.enabled"
    value = var.prometheus_enabled
  }

  # Add annotations for Prometheus auto-discovery
  set {
    name  = "podAnnotations.prometheus\\.io/scrape"
    value = var.prometheus_enabled ? "true" : "false"
  }

  set {
    name  = "podAnnotations.prometheus\\.io/path"
    value = "/actuator/prometheus"
  }

  set {
    name  = "podAnnotations.prometheus\\.io/port"
    value = "8080"
  }
}

output "app_status" {
  value = helm_release.app.status
}

output "prometheus_integration" {
  value = var.prometheus_enabled ? "Enabled with Pod Annotations" : "Disabled"
}
