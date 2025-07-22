pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/vuphamngocthanhdev/E-Commerce.git',
                    branch: 'dev',
                    credentialsId: 'github-creds'
            }
        }

        stage('Build') {
            steps {
                echo 'Building your application...'
            }
        }
    }
}
