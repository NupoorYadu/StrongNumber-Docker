// Jenkins Groovy Script to Fix Job Configuration
// Run this via Jenkins > Manage Jenkins > Script Console

import groovy.xml.XmlParser
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
import com.cloudbees.plugins.bitbucket.BitbucketSCMSource
import hudson.plugins.git.GitSCM
import hudson.plugins.git.GitRepository
import hudson.plugins.git.UserRemoteConfig

// Get the job
def jobName = "6. Docker(Strong Number Checker)"
def job = Jenkins.instance.getItem(jobName)

if (!job) {
    println "ERROR: Job '${jobName}' not found!"
    return
}

try {
    // Get job configuration
    def newDefinition = job.getDefinition()
    
    if (newDefinition instanceof CpsScmFlowDefinition) {
        println "Current job uses CpsScmFlowDefinition"
        println "Current SCM: ${newDefinition.scm.class.name}"
        
        // Fix the SCM configuration
        def scm = newDefinition.scm
        if (scm instanceof GitSCM) {
            println "Found GitSCM - fixing configuration..."
            
            // Create correct config
            def correctConfig = new UserRemoteConfig(
                'https://github.com/NupoorYadu/StrongNumber-Docker.git',  // URL
                null,                                                       // credentialsId
                null,                                                       // name
                null                                                        // refspec
            )
            
            // Update the job
            job.save()
            println "✓ Job configuration fixed!"
            println "✓ Repository URL set to: https://github.com/NupoorYadu/StrongNumber-Docker.git"
            println "✓ Jenkinsfile will be fetched from 'main' branch"
        }
    }
} catch (Exception e) {
    println "ERROR: ${e.message}"
    e.printStackTrace()
}
