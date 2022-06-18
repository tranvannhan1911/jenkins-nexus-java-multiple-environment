pipeline {
    agent any 
    stages {
        stage("Clone"){
            steps {
                git branch: 'main', url: 'https://github.com/tranvannhan1911/java-multiple-environment.git'
            }
        }
        
        stage("Checkout"){
            steps {
                checkout scm
            }
        }
        
        stage("Build"){
            steps {
                sh "mvn -f . -Dprofile=$ENV package"
            }
        }
    }
}