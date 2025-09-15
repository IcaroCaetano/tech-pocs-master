# Platform-Specific Configuration

This document provides platform-specific configuration instructions to ensure the system works properly on both Windows and Mac.

## Windows Configuration

1. **Hosts File Configuration**:
   - Open Notepad as Administrator
   - Open the file `C:\Windows\System32\drivers\etc\hosts`
   - Add the following line at the end of the file:
     ```
     127.0.0.1 k3s
     ```
   - Save the file

2. **Docker Socket Configuration**:
   - In the docker-compose.yml file, replace:
     ```yaml
     - /var/run/docker.sock:/var/run/docker.sock
     ```
   - With:
     ```yaml
     - //./pipe/docker_engine:/var/run/docker.sock
     ```

## Mac Configuration

1. **Hosts File Configuration**:
   - Open Terminal
   - Run the following command:
     ```bash
     sudo nano /etc/hosts
     ```
   - Add the following line at the end of the file:
     ```
     127.0.0.1 k3s
     ```
   - Press Ctrl+O to save and Ctrl+X to exit

## Why Host File Configuration is Needed

The host file configuration is required for the Kubernetes tools to work properly. It maps the `k3s` service name to `127.0.0.1`, allowing your local machine to resolve the k3s service name to localhost.

This is necessary because:

1. Inside the Docker Compose network, containers can communicate with each other using service names (e.g., "k3s")
2. The kubeconfig.yaml file is configured to use `https://k3s:6443` as the server URL
3. When you run kubectl or other tools on your host machine, they need to be able to resolve "k3s" to your localhost

Without this configuration, tools running on your host machine would not be able to connect to the Kubernetes API server.