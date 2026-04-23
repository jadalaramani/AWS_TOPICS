def call(Map config) {

    pipeline {
        agent any

        environment {
            IMAGE_NAME = config.imageName
            TAG = "${BUILD_NUMBER}"
        }

        stages {

            stage('Checkout') {
                steps {
                    gitCheckout(config.repoUrl)
                }
            }

            stage('Build Docker Image') {
                steps {
                    dockerBuild(IMAGE_NAME, TAG)
                }
            }

            stage('Push Image') {
                steps {
                    dockerPush(IMAGE_NAME, TAG, config.dockerCred)
                }
            }

            stage('Deploy') {
                steps {
                    echo "Deploying ${IMAGE_NAME}:${TAG}"
                }
            }
        }
    }
}
