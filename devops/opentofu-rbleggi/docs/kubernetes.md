# Kubernetes Deployment

This document provides information about the Kubernetes deployment of the DevOps Deployment System.

## Overview

This system is designed to be deployed to a Kubernetes cluster using OpenTofu and Helm. The deployment is organized into two namespaces:

- **Infrastructure Namespace**: Contains all the infrastructure components (Jenkins, Prometheus, Grafana)
  - Managed by OpenTofu
  - Provides services for CI/CD, monitoring, and observability
  - Configured in `opentofu/main.tf`

- **Applications Namespace**: Contains the deployed applications
  - Applications are deployed here by Helm
  - Separated from infrastructure for better isolation and management
  - Referenced in the Jenkins pipeline as the deployment target

## Using the Kubernetes Tools

After starting the Docker Compose environment, you can interact with the Kubernetes tools as follows:

1. **Wait for K3s to initialize** (this may take a minute or two)
2. **Verify K3s is running**:

   **All platforms:**
   **Windows (PowerShell):**
   ```sh
   $env:KUBECONFIG = ".\k3s\kubeconfig.yaml"; kubectl get nodes
   ```

4. **Use Helm to deploy applications**:

   ```sh
   docker-compose exec helm helm install sample-app /apps/sample-app
   ```

5. **Use OpenTofu to provision infrastructure**:

   ```sh
   docker-compose exec opentofu tofu init
   ```
   ```sh
   docker-compose exec opentofu tofu apply
   ```

## Kubernetes Configuration

### K3s Configuration

K3s is a lightweight Kubernetes distribution that runs as a Docker container in the Docker Compose setup:

- Kubernetes API available at: `https://localhost:6443` (from host) or `https://k3s:6443` (from containers)
- Kubeconfig file generated at: `./k3s/kubeconfig.yaml` and configured to use `k3s` service name for container access

### Helm Configuration

Helm is the Kubernetes package manager used to deploy applications:

- Available as a Docker container in the Docker Compose setup
- Access the Helm CLI with: `docker-compose exec helm helm [commands]`
- Pre-configured to connect to the K3s cluster
- Helm charts are located in the `helm` directory

### OpenTofu Configuration

OpenTofu is the Infrastructure as Code tool used to provision resources:

- Available as a Docker container in the Docker Compose setup
- Access the OpenTofu CLI with: `docker-compose exec opentofu tofu [commands]`
- Pre-configured to connect to the K3s cluster
- OpenTofu configuration is located in the `opentofu` directory



   docker-compose logs k3s
   ```

3. **Use kubectl with the generated kubeconfig**:

   **Mac/Linux (bash):**
   ```bash
   export KUBECONFIG=./k3s/kubeconfig.yaml
   kubectl get nodes
   ```

   **Windows (PowerShell):**
   ```sh
   $env:KUBECONFIG = ".\k3s\kubeconfig.yaml"; kubectl get nodes
   ```

4. **Use Helm to deploy applications**:

   ```sh
   docker-compose exec helm helm install sample-app /apps/sample-app
   ```

5. **Use OpenTofu to provision infrastructure**:

   ```sh
   docker-compose exec opentofu tofu init
   ```
   ```sh
   docker-compose exec opentofu tofu apply
   ```

## Kubernetes Configuration

### K3s Configuration

K3s is a lightweight Kubernetes distribution that runs as a Docker container in the Docker Compose setup:

- Kubernetes API available at: `https://localhost:6443` (from host) or `https://k3s:6443` (from containers)
- Kubeconfig file generated at: `./k3s/kubeconfig.yaml` and configured to use `k3s` service name for container access

### Helm Configuration

Helm is the Kubernetes package manager used to deploy applications:

- Available as a Docker container in the Docker Compose setup
- Access the Helm CLI with: `docker-compose exec helm helm [commands]`
- Pre-configured to connect to the K3s cluster
- Helm charts are located in the `helm` directory

### OpenTofu Configuration

OpenTofu is the Infrastructure as Code tool used to provision resources:

- Available as a Docker container in the Docker Compose setup
- Access the OpenTofu CLI with: `docker-compose exec opentofu tofu [commands]`
- Pre-configured to connect to the K3s cluster
- OpenTofu configuration is located in the `opentofu` directory


