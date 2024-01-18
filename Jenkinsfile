pipeline {
    agent any
    
    tools{
        jdk 'jdk17'
        maven 'maven3'
    }
    
    // environment{
    //     scannerHome= tool 'sonarqube'
    // }

    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/pakhitodkari/JWT-Spring-Security.git'
            }
        }
         stage('Compile') {
            steps {
                bat '''mvn clean package
'''
            }
         }    
         stage('SonarQube') {
            steps {
                   bat ''' mvn sonar:sonar -Dsonar.url=http://localhost:9000/ -Dsonar.login=sqp_e88869fc6e1cfb33a45027cf680a30f45d7e8388 -Dsonar.projectName=jwt-security \
                -Dsonar.java.binaries=. \
                -Dsonar.projectKey=jwt-security '''
            }
        }
    }
}
