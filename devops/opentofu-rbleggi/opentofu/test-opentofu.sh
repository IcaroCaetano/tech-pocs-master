#!/bin/bash

# Script for testing OpenTofu configurations
# This validates the OpenTofu configuration and verifies expected outputs

set -e # Exit immediately if a command exits with a non-zero status

echo "=== Running OpenTofu tests ==="

# Move to the OpenTofu directory
cd "$(dirname "$0")"

echo "=== Initializing OpenTofu ==="
tofu init

echo "=== Validating OpenTofu configuration ==="
tofu validate || { echo "Validation failed"; exit 1; }

echo "=== Running plan to verify expected resources ==="
PLAN_OUTPUT=$(tofu plan -no-color)

# Verify that essential resources are present in the plan
check_resource() {
    RESOURCE_TYPE=$1
    RESOURCE_NAME=$2

    if echo "$PLAN_OUTPUT" | grep -q "$RESOURCE_TYPE.*\"$RESOURCE_NAME\""; then
        echo "✓ Found resource: $RESOURCE_TYPE.$RESOURCE_NAME"
    else
        echo "✗ Missing expected resource: $RESOURCE_TYPE.$RESOURCE_NAME"
        exit 1
    fi
}

# Check for essential infrastructure components
check_resource "kubernetes_namespace" "infrastructure"
check_resource "kubernetes_namespace" "applications"
check_resource "helm_release" "prometheus"
check_resource "helm_release" "grafana"

echo "=== Testing Prometheus module ==="
if [ -f "modules/prometheus/main.tf" ]; then
    tofu validate -check-variables=false modules/prometheus
    echo "✓ Prometheus module is valid"
else
    echo "✗ Prometheus module is missing"
    exit 1
fi

echo "=== Testing Grafana module ==="
if [ -f "modules/grafana/main.tf" ]; then
    tofu validate -check-variables=false modules/grafana
    echo "✓ Grafana module is valid"
else
    echo "✗ Grafana module is missing"
    exit 1
fi

echo "=== All OpenTofu tests passed ==="
exit 0
