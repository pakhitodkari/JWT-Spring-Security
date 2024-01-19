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
        //Another Security Check using owasp
        stage('OWASP SCAN') {
            steps {
               dependencyCheck additionalArguments: ' --scan ./', odcInstallation: 'DP'
               dependencyCheckPublisher pattern: '***/dependency-check-report.xml'
            }
        }
         stage('Build Application') {
            steps {
               bat "mvn clean install"
            }
        }
         stage('Build Docker Image') {
            steps {
                script{
                   bat "docker build -t prachi098/security-jwt ."
                }
            }
        }
        stage('Push Docker Image To Hub by Login') {
            steps {
               script{
                   withCredentials([string(credentialsId: 'docker-cred', variable: 'dockercred')]) {
                        bat "docker login -u prachi098 -p ${dockercred}"
                        bat "docker push prachi098/security-jwt" 
                    }
               }
            }
        }
        stage('Trigger CD Pipeline') {
            steps {
                build job: "cd_pipeline2" , wait: true
            }
        }
    }
}