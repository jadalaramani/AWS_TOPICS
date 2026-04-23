def call(String imageName, String tag, String credId) {
    withCredentials([string(credentialsId: credId, variable: 'dockerPass')]) {
        sh """
        docker login -u your-username -p ${dockerPass}
        docker push ${imageName}:${tag}
        """
    }
}
