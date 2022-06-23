pipeline {
    agent any 
    
    tools {
        maven "Maven"
    }
    
    environment {
        GIT_REPOSITORY = "https://github.com/tranvannhan1911/java-multiple-environment.git"
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "https"
        NEXUS_URL = "nexus.tranvannhan1911.tk"
        NEXUS_CREDENTIAL_ID = "nexus"
        NEXUS_REPOSITORY_RELEASE = "example-maven-releases"
        NEXUS_REPOSITORY_SNAPSHOT = "example-maven-snapshots"
    }
    
    stages {
        stage("Checkout"){
            steps {
                checkout([$class: 'GitSCM', 
                    branches: [[name: '*/main']],
                    extensions: [[$class: 'CleanCheckout']], // clean workspace after checkout
                    userRemoteConfigs: [[url: GIT_REPOSITORY]]])
            }
        }
        
        stage("Build"){
            steps {
                sh "mvn -f . -Dprofile=$ENV package"
            }
        }
        
        stage("Publish to Nexus"){
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    version = pom.version;
                    versionType = version.substring(version.indexOf('-')+1, version.length());
                    artifactPath = filesByGlob[0].path;
                    
                    echo "${filesByGlob[0].name} ${artifactPath} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified} ${versionType}"
                    
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        if(versionType == "SNAPSHOT")
                            nexusRepository = NEXUS_REPOSITORY_SNAPSHOT;
                        else
                            nexusRepository = NEXUS_REPOSITORY_RELEASE;
                        
                        echo "File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        result = nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: nexusRepository,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                        
                        if(!result){
                            error "Upload artifact ${filesByGlob[0].name} fail!";
                        }
                    } else {
                        error "File: ${artifactPath}, could not be found";
                    }
                }
            }
        }
    }
}