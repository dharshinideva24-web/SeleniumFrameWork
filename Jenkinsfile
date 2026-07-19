pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    environment {
        BROWSERSTACK_USERNAME = credentials('browserstack-username')
        BROWSERSTACK_ACCESS_KEY = credentials('browserstack-accesskey')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-pat',
                    url: 'https://github.com/dharshinideva24-web/SeleniumFrameworkProject.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }
        stage('BrowserStack Tests') {
            steps {
                bat 'mvn test -PRegression'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}