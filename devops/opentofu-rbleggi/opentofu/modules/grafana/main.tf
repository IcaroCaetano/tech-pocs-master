# Grafana module for OpenTofu

variable "namespace" {
  description = "The namespace where Grafana will be deployed"
  type        = string
  default     = "infrastructure"
}

variable "grafana_helm_chart_version" {
  description = "The version of the Grafana Helm chart to deploy"
  type        = string
  default     = "6.50.7"
}

# Deploy Grafana using Helm
resource "helm_release" "grafana" {
  name       = "grafana"
  repository = "https://grafana.github.io/helm-charts"
  chart      = "grafana"
  version    = var.grafana_helm_chart_version
  namespace  = var.namespace

  values = [
    file("${path.module}/values.yaml")
  ]

  set {
    name  = "persistence.enabled"
    value = "true"
  }

  set {
    name  = "persistence.size"
    value = "5Gi"
  }

  set {
    name  = "resources.requests.cpu"
    value = "200m"
  }

  set {
    name  = "resources.requests.memory"
    value = "256Mi"
  }

  set {
    name  = "resources.limits.cpu"
    value = "500m"
  }

  set {
    name  = "resources.limits.memory"
    value = "512Mi"
  }

  set {
    name  = "service.type"
    value = "ClusterIP"
  }

  # Configure Prometheus as a data source
  set {
    name  = "datasources.datasources\\.yaml.apiVersion"
    value = "1"
  }

  set {
    name  = "datasources.datasources\\.yaml.datasources[0].name"
    value = "Prometheus"
  }

  set {
    name  = "datasources.datasources\\.yaml.datasources[0].type"
    value = "prometheus"
  }

  set {
    name  = "datasources.datasources\\.yaml.datasources[0].url"
    value = "http://prometheus-server.${var.namespace}.svc.cluster.local:80"
  }

  set {
    name  = "datasources.datasources\\.yaml.datasources[0].access"
    value = "proxy"
  }

  set {
    name  = "datasources.datasources\\.yaml.datasources[0].isDefault"
    value = "true"
  }
}

# Output the Grafana URL
output "grafana_url" {
  description = "URL to access Grafana"
  value       = "http://grafana.${var.namespace}.svc.cluster.local:80"
}

# Output the Grafana admin password command
output "grafana_admin_password" {
  description = "Command to get the Grafana admin password"
  value       = "kubectl get secret --namespace ${var.namespace} grafana -o jsonpath='{.data.admin-password}' | base64 --decode"
}