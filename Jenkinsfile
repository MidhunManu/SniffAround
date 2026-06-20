pipeline {

    agent any

    environment {
        JWT_SECRET = credentials('JWT_SECRET')
        JWT_EXPIRATION = credentials('JWT_EXPIRATION')
        JWT_REFRESH_TOKEN_EXPIRATION = credentials('JWT_REFRESH_TOKEN_EXPIRATION')

        DB_USERNAME = credentials('DB_USERNAME')
        DB_PASSWORD = credentials('DB_PASSWORD')
        DB_NAME = credentials('DB_NAME')

         FLYWAY_USER = credentials('DB_USERNAME')
         FLYWAY_PASSWORD = credentials('DB_PASSWORD')
         FLYWAY_URL = "jdbc:postgresql://localhost:5432/${DB_NAME}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test -Dspring.profiles.active=test'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Migration') {
            steps {
                sh './mvnw flyway:migrate'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker-compose build'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker-compose down
                docker-compose up -d
                '''
            }
        }

    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }

}