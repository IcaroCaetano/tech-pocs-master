# OpenTofu configuration for infrastructure components

terraform {
  required_version = ">= 1.0.0"
  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.0"
    }
    helm = {
      source  = "hashicorp/helm"
      version = "~> 2.0"
    }
  }
}

# Configure the Kubernetes provider
provider "kubernetes" {
  config_path    = var.kubeconfig_path
  config_context = "default"
}

# Configure the Helm provider
provider "helm" {
  kubernetes {
    config_path    = var.kubeconfig_path
    config_context = "default"
  }
}

variable "kubeconfig_path" {
  description = "Path to the kubeconfig file"
  type        = string
  default     = "/config/kubeconfig.yaml"
}

# Create infrastructure namespace if it doesn't exist
resource "kubernetes_namespace" "infrastructure" {
  metadata {
    name = "infrastructure"
  }
  lifecycle {
    prevent_destroy = true
    ignore_changes = all
  }
}

# Create applications namespace if it doesn't exist
resource "kubernetes_namespace" "applications" {
  metadata {
    name = "applications"
  }
  lifecycle {
    prevent_destroy = true
    ignore_changes = all
  }
}

# Include infrastructure monitoring components
module "prometheus" {
  source = "./modules/prometheus"
  namespace = kubernetes_namespace.infrastructure.metadata[0].name
}

module "grafana" {
  source = "./modules/grafana"
  namespace = kubernetes_namespace.infrastructure.metadata[0].name
  depends_on = [module.prometheus]
}

# Deploy application with Prometheus and Grafana integration
module "app" {
  source = "./modules/app"
  namespace = kubernetes_namespace.applications.metadata[0].name
  app_name = "java-app"
  app_image = "tech-pocs/java-devops-app"
  app_tag = "latest"
  prometheus_enabled = true
  depends_on = [
    module.prometheus,
    module.grafana
  ]
}

# Output para validação automática (teste)
output "test_namespaces_created" {
  value = "${kubernetes_namespace.infrastructure.metadata[0].name} and ${kubernetes_namespace.applications.metadata[0].name} successfully created"
}

output "test_monitoring_deployed" {
  value = "Monitoring stack deployed: Prometheus, Grafana in ${kubernetes_namespace.infrastructure.metadata[0].name} namespace"
  depends_on = [
    module.prometheus,
    module.grafana
  ]
}
