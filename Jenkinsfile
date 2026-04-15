pipeline {
    agent any

    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    environment {
        DOCKER_IMAGE_NAME = 'strong-number-checker'
        DOCKER_IMAGE_TAG = '1.0.0'
        REGISTRY = 'docker.io'
        SONARQUBE_DISABLED = 'true'
        GIT_URL = 'https://github.com/NupoorYadu/StrongNumber-Docker.git'
        GIT_BRANCH = 'main'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '========== Stage 1: Checkout =========='
                script {
                    // Clean workspace first
                    deleteDir()
                }
                // Direct git clone - most reliable
                bat """
                    git clone --branch ${GIT_BRANCH} ${GIT_URL} .
                    git log --oneline -5
                """
            }
        }

        stage('Build') {
            steps {
                echo '========== Stage 2: Build =========='
                bat 'mvn clean compile'
            }
        }

        stage('Automated Unit Tests') {
            steps {
                echo '========== Stage 3: Run Tests =========='
                bat 'mvn test'
            }
        }

        stage('Code Coverage') {
            steps {
                echo '========== Stage 4: Code Coverage =========='
                bat 'mvn jacoco:report'
                echo "✓ JaCoCo report generated: target/site/jacoco/index.html"
            }
        }

        stage('Code Quality Analysis') {
            when {
                expression { env.SONARQUBE_DISABLED == 'false' }
            }
            steps {
                echo '========== Stage 5: SonarQube Analysis =========='
                bat 'mvn sonar:sonar'
            }
        }

        stage('Package') {
            steps {
                echo '========== Stage 6: Package =========='
                bat 'mvn package -DskipTests'
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo '========== Stage 7: Archive =========='
                archiveArtifacts artifacts: 'target/strong-number-*.jar', allowEmptyArchive: false
                archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: true
            }
        }

        stage('Docker Build & Push') {
            steps {
                echo '========== Stage 8: Docker Build =========='
                script {
                    bat '''
                        echo Building Docker image...
                        docker build -t %DOCKER_IMAGE_NAME%:%DOCKER_IMAGE_TAG% .
                        echo.
                        echo Docker image built successfully!
                        docker images | findstr %DOCKER_IMAGE_NAME%
                    '''
                }
            }
        }

        stage('Demo Execution') {
            steps {
                echo '========== Stage 9: Demo Execution =========='
                bat '''
                    echo Running Docker container...
                    docker run --rm %DOCKER_IMAGE_NAME%:%DOCKER_IMAGE_TAG%
                '''
            }
        }

        stage('Build Summary') {
            steps {
                echo '========== Stage 10: Build Summary =========='
                script {
                    echo "Build Number: ${BUILD_NUMBER}"
                    echo "Build Status: SUCCESS"
                    echo "Total Stages Completed: 10"
                    bat '''
                        echo.
                        echo ========== Build Summary ==========
                        echo Docker Image: %DOCKER_IMAGE_NAME%:%DOCKER_IMAGE_TAG%
                        echo JAR File: target\\strong-number-1.0.0.jar
                        echo Repository: https://github.com/NupoorYadu/StrongNumber-Docker
                        echo ===================================
                    '''
                }
            }
        }
    }

    post {
        always {
            junit testResults: '**/target/surefire-reports/TEST-*.xml', allowEmptyResults: true
        }
        success {
            echo "✓ Pipeline executed successfully!"
        }
        failure {
            echo "✗ Pipeline failed. Check logs for details."
        }
    }
}
