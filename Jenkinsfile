pipeline {
    agent any

    environment {
        REACT_APP_DIR = 'src/main/dcloudreact'
        SPRING_BOOT_DIR = '.'
        IMAGE_REACT = 'parkmingyun99/react-app'
        IMAGE_SPRINGBOOT = 'parkmingyun99/springboot-app'
    }

    stages {
        stage('Checkout') {
            steps {
                // GitHub에서 코드 체크아웃
                git 'https://github.com/DGU-Dcloud/Dcloud.git'
            }
        }
        stage('Build React App') {
            steps {
                dir("${REACT_APP_DIR}") {
                    // React 애플리케이션 빌드
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }
        stage('Build Spring Boot App') {
            steps {
                dir("${SPRING_BOOT_DIR}") {
                    // Spring Boot 애플리케이션 빌드
                    sh './gradlew build -x test'
                }
            }
        }
        stage('Build Docker Images') {
            steps {
                script {
                    // React Docker 이미지 빌드
                    sh 'docker build -t ${IMAGE_REACT} ${REACT_APP_DIR}'

                    // Spring Boot Docker 이미지 빌드
                    sh 'docker build -t ${IMAGE_SPRINGBOOT} ${SPRING_BOOT_DIR}'
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // 기존 컨테이너 중지 및 제거
                    sh 'docker stop react-app || true && docker rm react-app || true'
                    sh 'docker stop springboot-app || true && docker rm springboot-app || true'

                    // 새로운 컨테이너 실행
                    sh 'docker run -d --name react-app -p 80:80 ${IMAGE_REACT}'
                    sh 'docker run -d --name springboot-app -p 8080:8080 ${IMAGE_SPRINGBOOT}'
                }
            }
        }
    }

    post {
        always {
            // 항상 로그를 기록
            archiveArtifacts artifacts: '**/build/libs/*.jar', allowEmptyArchive: true
            junit 'build/test-results/test/*.xml'
        }
        success {
            echo 'Deployment succeeded'
        }
        failure {
            echo 'Deployment failed'
        }
    }
}

