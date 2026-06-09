pipeline {

       agent any
       tools {
            jdk "JDK25"
            maven "3.9.14"
       }
       stages {
            stage('compile') {
                steps {
                    script {
                        if(isUnix()){
                            sh 'mvn clean compile'
                        }
                        else{
                            bat 'mvn clean compile'
                        }
                    }
                 }
            }
            stage('build') {
                steps{
                    script {
                        if(isUnix()){
                            sh 'mvn test -PJiraCreateBug'
                        }
                        else{
                            bat 'mvn test -PJiraCreateBug'
                        }
                    }
                }

            }
       }

       post {
            always {
                junit '**/target/surefire-reports/*.xml'
                allure results [[path : 'target/allure-results' ]]
            }
            success {
                echo 'build success'
            }
            failure {
                echo 'build failure'
            }
        }





}