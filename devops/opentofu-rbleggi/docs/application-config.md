# Configuring and Integrating Applications

This document provides detailed instructions on how to configure and integrate the various applications in the system.

## Jenkins Configuration

Jenkins is pre-configured with the necessary plugins and a sample pipeline, but you may want to customize it for your specific needs.

### Initial Setup

1. **Access Jenkins**: Open [http://localhost:8081](http://localhost:8081) in your browser.

2. **Login**: 
   - Username: `admin`
   - Password: The initial admin password can be found in the Jenkins logs:
     ```sh
     docker-compose logs jenkins
     ```
   - Procure pela linha que contém "Jenkins initial setup" no resultado.

3. **Install Suggested Plugins**: If prompted, install the suggested plugins.

### Creating a Pipeline

1. **Create a New Pipeline**:
   - Click on "New Item" in the Jenkins dashboard
   - Enter a name for your pipeline
   - Select "Pipeline" and click "OK"

2. **Configure the Pipeline**:
   - In the "Pipeline" section, select "Pipeline script from SCM"
   - Select "Git" as the SCM
   - Enter your repository URL
   - Specify the branch to build
   - Set the Script Path to `jenkins/pipelines/Jenkinsfile`
   - Click "Save"

3. **Run the Pipeline**:
   - Click on your pipeline in the Jenkins dashboard
   - Click "Build Now"

### Integrating with Kubernetes

Jenkins is configured to use the Kubernetes plugin to run build agents as pods in the Kubernetes cluster:

1. **Configure Kubernetes Cloud**:
   - Go to "Manage Jenkins" > "Manage Nodes and Clouds" > "Configure Clouds"
   - Click "Add a new cloud" > "Kubernetes"
   - Set the Kubernetes URL to `https://k3s:6443`
   - Set the Kubernetes Namespace to `infrastructure`
   - Set the Jenkins URL to `http://jenkins.infrastructure.svc.cluster.local:8080`
   - Click "Save"

2. **Use Kubernetes in Pipelines**:
   - Use the `kubernetes` agent in your Jenkinsfile:
     ```groovy
     agent {
       kubernetes {
         yaml """
         apiVersion: v1
         kind: Pod
         spec:
           containers:
           - name: maven
             image: maven:3.8.6-openjdk-11
             command:
             - sleep
             args:
             - 99d
         """
       }
     }
     ```

## Prometheus Configuration

Prometheus is configured to scrape metrics from the Spring Boot API, Jenkins, and itself.

### Accessing Prometheus

1. **Open Prometheus**: Navigate to [http://localhost:9090](http://localhost:9090) in your browser.

2. **Explore Metrics**: 
   - Click on "Graph" in the top navigation bar
   - Start typing in the search box to see available metrics
   - Use the "Execute" button to run queries

### Adding Scrape Targets

1. **Edit the Prometheus Configuration**:
   - The configuration file is located at `prometheus/prometheus.yml`
   - Add a new scrape configuration under the `scrape_configs` section:
     ```yaml
     - job_name: 'new-target'
       metrics_path: '/metrics'
       static_configs:
         - targets: ['hostname:port']
     ```

2. **Reload the Configuration**:
   - Reinicie o Prometheus:
     ```sh
     docker-compose restart prometheus
     ```
   - Ou use a API HTTP (se habilitada):
     ```sh
     curl -X POST http://localhost:9090/-/reload
     ```

3. **Verify the Target**:
   - Go to the "Status" > "Targets" page in Prometheus
   - Check that your new target is listed and "UP"

## Grafana Configuration

Grafana is pre-configured with Prometheus as a data source and some basic dashboards.

### Initial Setup

1. **Access Grafana**: Open [http://localhost:3000](http://localhost:3000) in your browser.

2. **Login**:
   - Username: `admin`
   - Password: `admin`
   - You'll be prompted to change the password on first login

### Exploring Dashboards

1. **View Existing Dashboards**:
   - Click on the "Dashboards" icon in the left sidebar
   - Select "Browse" to see all available dashboards

2. **Create a New Dashboard**:
   - Click on the "+" icon in the left sidebar
   - Select "Dashboard"
   - Click "Add new panel"
   - Configure the panel with Prometheus queries
   - Click "Save" to save your dashboard

### Adding Data Sources

1. **Add a New Data Source**:
   - Click on the "Configuration" (gear) icon in the left sidebar
   - Select "Data sources"
   - Click "Add data source"
   - Select the type of data source (e.g., Prometheus, Elasticsearch)
   - Configure the connection details
   - Click "Save & Test"

2. **Use the Data Source in Dashboards**:
   - When creating or editing a panel, select your data source from the dropdown
   - Write queries specific to that data source
   - Use variables and templates for dynamic dashboards

### Setting Up Alerts

1. **Create an Alert Rule**:
   - Edit a panel in a dashboard
   - Click on the "Alert" tab
   - Define the alert conditions
   - Set the evaluation interval and for how long the condition must be true
   - Add notifications channels
   - Click "Save"

2. **Configure Notification Channels**:
   - Go to "Alerting" > "Notification channels"
   - Click "New channel"
   - Select the type (e.g., Email, Slack, Webhook)
   - Configure the channel settings
   - Click "Save"

## Integrating the Components

The components in this system are designed to work together seamlessly:

1. **API to Prometheus Integration**:
   - The Spring Boot API exposes metrics at `/actuator/prometheus`
   - Prometheus is configured to scrape these metrics
   - View API metrics in Prometheus by querying for metrics with the `api` label

2. **Prometheus to Grafana Integration**:
   - Prometheus is configured as a data source in Grafana
   - Use Prometheus queries in Grafana dashboards to visualize metrics
   - Example query: `rate(http_server_requests_seconds_count{job="spring-boot-api"}[5m])`

3. **Jenkins to Kubernetes Integration**:
   - Jenkins uses the Kubernetes plugin to run build agents as pods
   - The Jenkinsfile defines the pod templates for build agents
   - Jenkins deploys applications to Kubernetes using Helm

4. **Helm to Kubernetes Integration**:
   - Helm is used to package and deploy applications to Kubernetes
   - The Helm charts are located in the `helm` directory
   - Jenkins uses Helm in the pipeline to deploy applications

5. **OpenTofu to Kubernetes Integration**:
   - OpenTofu provisions infrastructure resources on Kubernetes
   - The OpenTofu configuration is located in the `opentofu` directory
   - Jenkins uses OpenTofu in the pipeline to provision infrastructure
