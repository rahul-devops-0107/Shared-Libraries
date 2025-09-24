def git-checkout(repo)
{
    git "https://github.com/IntelliqDevops/${repo}.git" // We are replacing the repo name so as to reuse it in multiple pipelines
}

def build-artifact()
{
    sh 'mv clean package' // We are not using any variables here because the command is common in every pipeline and "clean package" is used so as to reduce the memory consumption of jenkins server as it deletes previous build artifacts once the new build stage is trigerred
}

def tomcat-deployment(jobname,ip_address,context) // Here we are using three variables to reuse in different deployment stages as three variable change during the deployment stages
{
    sh "scp /var/lib/jenkins/workspace/${jobname}/webapp/target/webapp.war vagrant@${ip_address}:var/lib/tomcat10/webapps/${context}.war"
}

def testing-run()
{
    sh "java -jar /var/lib/jenkins/workspace/${jobname}/testing.jar"
}

def email-notification()
{
    mail bcc: "", body: "${stage_error}.getMessage()", cc: "", from: "", replyTo: "", subject: "${subject_body}", to: "rahul.devops.0107@gmail.com"
    exit(1)
}
