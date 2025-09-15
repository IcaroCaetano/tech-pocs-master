# Prometheus module for OpenTofu

variable "namespace" {
  description = "The namespace where Prometheus will be deployed"
  type        = string
  default     = "infrastructure"
}

variable "prometheus_helm_chart_version" {
  description = "The version of the Prometheus Helm chart to deploy"
  type        = string
  default     = "19.6.0"
}

# Deploy Prometheus using Helm
resource "helm_release" "prometheus" {
  name       = "prometheus"
  repository = "https://prometheus-community.github.io/helm-charts"
  chart      = "prometheus"
  version    = var.prometheus_helm_chart_version
  namespace  = var.namespace

  values = [
    file("${path.module}/values.yaml")
  ]

  set {
    name  = "server.persistentVolume.enabled"
    value = "true"
  }

  set {
    name  = "server.persistentVolume.size"
    value = "8Gi"
  }

  set {
    name  = "alertmanager.persistentVolume.enabled"
    value = "true"
  }

  set {
    name  = "alertmanager.persistentVolume.size"
    value = "2Gi"
  }

  set {
    name  = "server.resources.requests.cpu"
    value = "500m"
  }

  set {
    name  = "server.resources.requests.memory"
    value = "512Mi"
  }

  set {
    name  = "server.resources.limits.cpu"
    value = "1000m"
  }

  set {
    name  = "server.resources.limits.memory"
    value = "1Gi"
  }
}

# Output the Prometheus server URL
output "prometheus_server_url" {
  description = "URL to access Prometheus server"
  value       = "http://prometheus-server.${var.namespace}.svc.cluster.local:80"
}

# Output the Prometheus Alertmanager URL
output "prometheus_alertmanager_url" {
  description = "URL to access Prometheus Alertmanager"
  value       = "http://prometheus-alertmanager.${var.namespace}.svc.cluster.local:80"
}