# Pipeline and Provisioning Logs

This file contains example logs and outputs from the deployment and provisioning process, including Jenkins pipeline steps, OpenTofu commands, Docker, Helm, and Kubernetes operations.

---

## OpenTofu Provisioning Logs

### tofu init
```
C:\ProgramData\Jenkins\.jenkins\workspace\infra\devops\opentofu-k8s-deploy\infra>tofu init

Initializing the backend...

Initializing provider plugins...
- Reusing previous version of tehcyx/kind from the dependency lock file
- Reusing previous version of hashicorp/kubernetes from the dependency lock file
- Reusing previous version of hashicorp/helm from the dependency lock file
- Installing tehcyx/kind v0.2.1...
- Installed tehcyx/kind v0.2.1. Signature validation was skipped due to the registry not containing GPG keys for this provider
- Installing hashicorp/kubernetes v2.36.0...
- Installed hashicorp/kubernetes v2.36.0 (signed, key ID 0C0AF313E5FD9F80)
- Installing hashicorp/helm v2.17.0...
- Installed hashicorp/helm v2.17.0 (signed, key ID 0C0AF313E5FD9F80)

  Providers are signed by their developers.
  If you'd like to know more about provider signing, you can read about it here:
  https://opentofu.org/docs/cli/plugins/signing/

  OpenTofu has made some changes to the provider dependency selections recorded
  in the .terraform.lock.hcl file. Review those changes and commit them to your
  version control system if they represent changes you intended to make.

  OpenTofu has been successfully initialized!

  You may now begin working with OpenTofu. Try running "tofu plan" to see
  any changes that are required for your infrastructure. All OpenTofu commands
  should now work.

  If you ever set or change modules or backend configuration for OpenTofu,
  rerun this command to reinitialize your working directory. If you forget, other
  commands will detect it and remind you to do so if necessary.
```

### tofu apply
```
C:\ProgramData\Jenkins\.jenkins\workspace\infra\devops\opentofu-k8s-deploy\infra>tofu apply -auto-approve 

OpenTofu used the selected providers to generate the following execution
plan. Resource actions are indicated with the following symbols:
  + create

OpenTofu will perform the following actions:

# helm_release.grafana will be created
# helm_release.prometheus will be created
# kind_cluster.default will be created
# kubernetes_namespace.apps will be created
# kubernetes_namespace.infra will be created

Plan: 5 to add, 0 to change, 0 to destroy.
kind_cluster.default: Creating...
kind_cluster.default: Still creating... [10s elapsed]
kind_cluster.default: Creation complete after 15s [id=poc-kind-cluster-]
kubernetes_namespace.infra: Creating...
kubernetes_namespace.apps: Creating...
kubernetes_namespace.infra: Creation complete after 0s [id=infra]
kubernetes_namespace.apps: Creation complete after 0s [id=apps]
helm_release.prometheus: Creating...
helm_release.grafana: Creating...
helm_release.prometheus: Still creating... [10s elapsed]
helm_release.grafana: Still creating... [10s elapsed]
helm_release.prometheus: Still creating... [20s elapsed]
helm_release.grafana: Still creating... [20s elapsed]
helm_release.prometheus: Still creating... [30s elapsed]
helm_release.grafana: Still creating... [30s elapsed]
helm_release.prometheus: Still creating... [40s elapsed]
helm_release.grafana: Still creating... [40s elapsed]
helm_release.grafana: Creation complete after 42s [id=grafana]
helm_release.prometheus: Still creating... [50s elapsed]
helm_release.prometheus: Still creating... [1m0s elapsed]
helm_release.prometheus: Still creating... [1m10s elapsed]
helm_release.prometheus: Still creating... [1m20s elapsed]
helm_release.prometheus: Creation complete after 1m21s [id=prometheus]

Apply complete! Resources: 5 added, 0 changed, 0 destroyed.
```

### tofu test
```
C:\ProgramData\Jenkins\.jenkins\workspace\infra\devops\opentofu-k8s-deploy\infra>tofu test 
main.tftest.hcl... pass
  run "namespace_infra"... pass
  run "namespace_apps"... pass
  run "prometheus_helm"... pass
  run "grafana_helm"... pass

Success! 4 passed, 0 failed.
```

---

## Jenkins Pipeline Logs

### git checkout
```
Started by user unknown or anonymous
Obtained devops/opentofu-k8s-deploy/Jenkinsfile from git https://github.com/rbleggi/tech-pocs.git
Running on Jenkins in C:\ProgramData\Jenkins\.jenkins\workspace\code
Selected Git installation does not exist. Using Default
The recommended git tool is: NONE
No credentials specified
 > git.exe rev-parse --resolve-git-dir C:\ProgramData\Jenkins\.jenkins\workspace\code\.git # timeout=10
Fetching changes from the remote Git repository
 > git.exe config remote.origin.url https://github.com/rbleggi/tech-pocs.git # timeout=10
Fetching upstream changes from https://github.com/rbleggi/tech-pocs.git
 > git.exe --version # timeout=10
 > git --version # 'git version 2.45.1.windows.1'
 > git.exe fetch --tags --force --progress -- https://github.com/rbleggi/tech-pocs.git +refs/heads/*:refs/remotes/origin/* # timeout=10
 > git.exe rev-parse "refs/remotes/origin/master^{commit}" # timeout=10
Checking out Revision fd61c90dd4279f03ea9b450ed69cb58ad0d003fd (refs/remotes/origin/master)
 > git.exe config core.sparsecheckout # timeout=10
 > git.exe checkout -f fd61c90dd4279f03ea9b450ed69cb58ad0d003fd # timeout=10
Commit message: "feat: update Jenkins pipeline and documentation for OpenTofu integration"
 > git.exe rev-list --no-walk c4e43d80d97929b66ccfe189ac7f8477187e6136 # timeout=10
Masking supported pattern matches of %GHCR_TOKEN%
```

### docker login
```
C:\ProgramData\Jenkins\.jenkins\workspace\code>echo ****   | docker login ghcr.io -u rbleggi --password-stdin 
Login Succeeded
Selected Git installation does not exist. Using Default
The recommended git tool is: NONE
No credentials specified
 > git.exe rev-parse --resolve-git-dir C:\ProgramData\Jenkins\.jenkins\workspace\code\.git # timeout=10
Fetching changes from the remote Git repository
 > git.exe config remote.origin.url https://github.com/rbleggi/tech-pocs.git # timeout=10
Fetching upstream changes from https://github.com/rbleggi/tech-pocs.git
 > git.exe --version # timeout=10
 > git --version # 'git version 2.45.1.windows.1'
 > git.exe fetch --tags --force --progress -- https://github.com/rbleggi/tech-pocs.git +refs/heads/*:refs/remotes/origin/* # timeout=10
 > git.exe rev-parse "refs/remotes/origin/master^{commit}" # timeout=10
Checking out Revision fd61c90dd4279f03ea9b450ed69cb58ad0d003fd (refs/remotes/origin/master)
 > git.exe config core.sparsecheckout # timeout=10
 > git.exe checkout -f fd61c90dd4279f03ea9b450ed69cb58ad0d003fd # timeout=10
Commit message: "feat: update Jenkins pipeline and documentation for OpenTofu integration"
```

### maven install
```
C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\app>mvn clean install -DskipTests
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< com.example:api >---------------------------
[INFO] Building api 0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.4.1:clean (default-clean) @ api ---
[INFO] Deleting C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\app\target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ api ---
[INFO] Copying 1 resource from src\main\resources to target\classes
[INFO] Copying 0 resource from src\main\resources to target\classes
[INFO] 
[INFO] --- compiler:3.13.0:compile (default-compile) @ api ---
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 1 source file with javac [debug parameters release 21] to target\classes
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ api ---
[INFO] skip non existing resourceDirectory C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\app\src\test\resources
[INFO] 
[INFO] --- compiler:3.13.0:testCompile (default-testCompile) @ api ---
[INFO] Recompiling the module because of changed dependency.
[INFO] Compiling 1 source file with javac [debug parameters release 21] to target\test-classes
[INFO] 
[INFO] --- surefire:3.5.2:test (default-test) @ api ---
[INFO] Tests are skipped.
[INFO] 
[INFO] --- jar:3.4.2:jar (default-jar) @ api ---
[INFO] Building jar: C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\app\target\api-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ api ---
[INFO] Installing C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\app\pom.xml to C:\WINDOWS\system32\config\systemprofile\.m2\repository\com\example\api\0.0.1-SNAPSHOT\api-0.0.1-SNAPSHOT.pom
[INFO] Installing C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\app\target\api-0.0.1-SNAPSHOT.jar to C:\WINDOWS\system32\config\systemprofile\.m2\repository\com\example\api\0.0.1-SNAPSHOT\api-0.0.1-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.011 s
[INFO] Finished at: 2025-07-28T09:58:16-03:00
[INFO] ------------------------------------------------------------------------
```

### maven test
```
C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\app>mvn test
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< com.example:api >---------------------------
[INFO] Building api 0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ api ---
[INFO] Copying 1 resource from src\main\resources to target\classes
[INFO] Copying 0 resource from src\main\resources to target\classes
[INFO] 
[INFO] --- compiler:3.13.0:compile (default-compile) @ api ---
[INFO] Nothing to compile - all classes are up to date.
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ api ---
[INFO] skip non existing resourceDirectory C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\app\src\test\resources
[INFO] 
[INFO] --- compiler:3.13.0:testCompile (default-testCompile) @ api ---
[INFO] Nothing to compile - all classes are up to date.
[INFO] 
[INFO] --- surefire:3.5.2:test (default-test) @ api ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 0, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.383 s
[INFO] Finished at: 2025-07-28T09:58:18-03:00
[INFO] ------------------------------------------------------------------------
```

### docker build
```
C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\app>docker build -t ghcr.io/rbleggi/sandbox/java-app:5 . 
#0 building with "default" instance using docker driver
...
#14 DONE 0.0s
Running in C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\app
```

### docker push
```
C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\app>docker push ghcr.io/rbleggi/sandbox/java-app:5 
The push refers to repository [ghcr.io/rbleggi/sandbox/java-app]
...
5: digest: sha256:1654ac1f4bc3f70aec3c36da589e0be086b66a34b3d5e29a637265b27a861a69 size: 1785
```

### helm lint
```
C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\helm\app>helm lint . 
==> Linting .
[INFO] Chart.yaml: icon is recommended

1 chart(s) linted, 0 chart(s) failed
```

### helm package
```
C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\helm\app>helm package . 
Successfully packaged chart and saved it to: C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\helm\app\java-app-0.1.0.tgz
```

### helm upgrade
```
C:\ProgramData\Jenkins\.jenkins\workspace\code\devops\opentofu-k8s-deploy\helm\app>helm upgrade --install java-app --set image.tag=5 . 
Release "java-app" has been upgraded. Happy Helming!
NAME: java-app
LAST DEPLOYED: Mon Jul 28 09:58:23 2025
NAMESPACE: default
STATUS: deployed
REVISION: 3
TEST SUITE: None
Finished: SUCCESS
```

