pipeline {
    agent any 
    stages {
        
        stage("Build"){
            steps {
                sh "mvn -f . -Dprofile=$ENV package"
            }
        }
    }
}