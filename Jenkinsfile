pipeline {
    agent any

    tools {
        maven 'maven-3.9'
    }

    environment {
        IMAGE_NAME = "ahmeddevop/simple-java-maven-app"
        TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$TAG .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {

                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'

                    sh 'docker push $IMAGE_NAME:$TAG'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {

                sh '''
                sed -i "s|IMAGE_PLACEHOLDER|$IMAGE_NAME:$TAG|g" deployment.yaml
                '''

                withCredentials([file(
                    credentialsId: 'kubeconfig',
                    variable: 'KUBECONFIG'
                )]) {

                    sh 'kubectl apply -f deployment.yaml'

                    sh 'kubectl apply -f service.yaml'

                    sh 'kubectl rollout status deployment/java-app'
                }
            }
        }
    }
}
