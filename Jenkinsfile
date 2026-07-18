pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-pat',
                    url: 'https://github.com/dharshinideva24-web/AutomationCICD.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Regression Tests') {
            steps {
                bat 'mvn test -PRegression'   // activates the profile that points at testSuite/testng.xml
            }
        }
    }

   post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
        success {
            echo 'All tests passed.'
        }
        failure {
            echo 'Regression run failed — check the reports.'
        }
    }
}