import jenkins.model.*
import hudson.model.*
import java.util.logging.Logger

Logger logger = Logger.getLogger("create-pipeline.groovy")
logger.info("Initializing pipeline creation script")

// Schedule the pipeline creation to be executed after initialization
Thread.start {
    logger.info("Starting pipeline creation")

    try {
        // Get environment variables - with specific values for the tech-pocs repository
        def env = System.getenv()
        def pipelineName = env['PIPELINE_NAME'] ?: 'java-devops-pipeline'
        def gitRepo = env['GIT_REPO'] ?: 'https://github.com/rbleggi/tech-pocs.git'
        def gitBranch = env['GIT_BRANCH'] ?: 'master'
        def jenkinsfilePath = env['JENKINSFILE_PATH'] ?: 'java/devops/Jenkinsfile'

        // Simple approach using Jenkins API
        def jenkins = Jenkins.getInstance()

        // Check if the pipeline already exists
        if (jenkins.getItem(pipelineName) != null) {
            logger.info("Pipeline ${pipelineName} already exists. Skipping creation.")
            return
        }

        logger.info("Creating pipeline ${pipelineName} for repository ${gitRepo}, branch ${gitBranch}, using Jenkinsfile ${jenkinsfilePath}")

        // Create XML configuration for the pipeline
        def configXml = """
<flow-definition plugin="workflow-job@1346.v180a_63f40e57">
  <description>Pipeline automatically created to build Dockerfile in the java/devops folder of repository ${gitRepo}</description>
  <keepDependencies>false</keepDependencies>
  <properties/>
  <definition class="org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition" plugin="workflow-cps@3773.v505e0f19c525">
    <scm class="hudson.plugins.git.GitSCM" plugin="git@5.1.0">
      <configVersion>2</configVersion>
      <userRemoteConfigs>
        <hudson.plugins.git.UserRemoteConfig>
          <url>${gitRepo}</url>
        </hudson.plugins.git.UserRemoteConfig>
      </userRemoteConfigs>
      <branches>
        <hudson.plugins.git.BranchSpec>
          <name>refs/heads/${gitBranch}</name>
        </hudson.plugins.git.BranchSpec>
      </branches>
      <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
      <submoduleCfg class="empty-list"/>
      <extensions/>
    </scm>
    <scriptPath>${jenkinsfilePath}</scriptPath>
    <lightweight>true</lightweight>
  </definition>
  <triggers/>
  <disabled>false</disabled>
</flow-definition>
"""

        // Create a temporary file with the configuration
        def configFile = File.createTempFile("jenkins-job-", ".xml")
        configFile.text = configXml
        logger.info("Configuration file created at ${configFile.absolutePath}")

        // Create the pipeline using the Java API directly
        jenkins.createProjectFromXML(pipelineName, new ByteArrayInputStream(configXml.getBytes("UTF-8")))
        jenkins.save()
        logger.info("Pipeline ${pipelineName} successfully created via Java API")

        // Run the pipeline?
        def autoRun = env['AUTO_RUN_PIPELINE'] ?: 'false'
        if (autoRun == 'true') {
            def job = jenkins.getItem(pipelineName)
            if (job != null) {
                job.scheduleBuild(new Cause.UserIdCause())
                logger.info("Pipeline ${pipelineName} build successfully scheduled")
            }
        }

    } catch (Exception e) {
        logger.severe("Error creating pipeline: ${e.message}")
        e.printStackTrace()

        // Try an alternative approach - create a file that can be executed manually
        try {
            def jenkins = Jenkins.getInstance()
            def jobDir = new File(jenkins.rootDir, "jobs/${env['PIPELINE_NAME'] ?: 'java-devops-pipeline'}")
            jobDir.mkdirs()

            def configFile = new File(jobDir, "config.xml")
            configFile.text = """
<flow-definition plugin="workflow-job@1346.v180a_63f40e57">
  <description>Pipeline automatically created to build Dockerfile in the java/devops folder</description>
  <keepDependencies>false</keepDependencies>
  <properties/>
  <definition class="org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition" plugin="workflow-cps@3773.v505e0f19c525">
    <scm class="hudson.plugins.git.GitSCM" plugin="git@5.1.0">
      <configVersion>2</configVersion>
      <userRemoteConfigs>
        <hudson.plugins.git.UserRemoteConfig>
          <url>${env['GIT_REPO'] ?: 'https://github.com/rbleggi/tech-pocs.git'}</url>
        </hudson.plugins.git.UserRemoteConfig>
      </userRemoteConfigs>
      <branches>
        <hudson.plugins.git.BranchSpec>
          <name>refs/heads/${env['GIT_BRANCH'] ?: 'main'}</name>
        </hudson.plugins.git.BranchSpec>
      </branches>
      <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
      <submoduleCfg class="empty-list"/>
      <extensions/>
    </scm>
    <scriptPath>${env['JENKINSFILE_PATH'] ?: 'java/devops/Jenkinsfile'}</scriptPath>
    <lightweight>true</lightweight>
  </definition>
  <triggers/>
  <disabled>false</disabled>
</flow-definition>
"""
            // Create file to force reload
            new File(jenkins.rootDir, "reload.txt").text = "Reload: ${new Date()}"

            logger.info("Manual configuration created at ${configFile.absolutePath}")
            logger.info("Restart Jenkins to apply the configuration")
        } catch (Exception ex) {
            logger.severe("Alternative approach also failed: ${ex.message}")
        }
    }
}

logger.info("Initialization script completed")
