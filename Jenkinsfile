pipeline{

    agent any

    tools{
        jdk "JDK25"
        maven "3.9.14"


    }
        stages{
        stage('build') {
        steps {
        bat 'mvn clean compile'
}
}
stage('execute tests') {
    steps {
    bat 'mvn test'
}
}

        }
        post{
        always {
        junit '**/target/surefire-reports/*.xml'
        allure results: [[path: 'target/allure-results']]}
success {
    echo 'build success'
}
        failure {
    echo 'build failure'
}
        }

}