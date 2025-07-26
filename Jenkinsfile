pipeline {
    agent any

    environment {
        GITHUB_REPO         = 'https://github.com/vuphamngocthanhdev/E-Commerce.git'
        BRANCH_NAME         = 'dev'
        JAVA_HOME           = '/usr/lib/jvm/java-21-openjdk-amd64'
        MVN_HOME            = '/usr/bin'
        PATH                = "${JAVA_HOME}/bin:${MVN_HOME}/mvn:$PATH"
        WORKSPACE_DIR       = '/home/chichi/E-Commerce'
        TELEGRAM_BOT_TOKEN  = 'TELEGRAM_BOT_TOKEN'
        TELEGRAM_CHAT_ID    = 'TELEGRAM_CHAT_ID'
    }

    stages {

        stage('Checkout') {
            steps {
                script {
                    if (fileExists(".git")) {
                        echo "Repository exists. Pulling latest changes..."
                        sh """
                            cd ${WORKSPACE_DIR}
                            git config --global --add safe.directory ${WORKSPACE_DIR}
                            git reset --hard
                            git pull origin ${BRANCH_NAME}
                        """
                    } else {
                        echo "Cloning repository..."
                        sh "git clone -b ${BRANCH_NAME} ${GITHUB_REPO} ."
                    }
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running Unit Tests...'
            }
        }

        stage('Integration Tests') {
            steps {
                echo 'Running Integration Tests...'
            }
        }

        stage('Code Quality') {
            steps {
                echo 'Analyzing Code Quality...'
            }
        }

        stage('Deploy to Staging') {
            steps {
                echo 'Deploying to staging environment...'
            }
        }
    }

    post {
        always {
            echo 'Executing post-build actions...'
        }

        success {
            script {
                def gitBranch   = sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
                def gitAuthor   = sh(script: "git log -1 --pretty=format:%an", returnStdout: true).trim()
                def timeStamp   = sh(script: "date '+%H:%M:%S %d-%m-%Y'", returnStdout: true).trim()

                // Lấy nhánh vừa merge nếu có (dành cho merge kiểu "Merge branch ... into dev")
                def mergedBranch = sh(
                    script: """git log --merges --oneline -n 1 | sed -En "s/.*from vuphamngocthanhdev\\/(.*)/\\1/p" """,
                    returnStdout: true
                ).trim()

                def message = """
                    🎉 *SUCCESS*

                    ✅ *Job:* ${env.JOB_NAME}
                    👤 *Author:* ${gitAuthor}
                    🌿 *Branch:* ${gitBranch}
                    🔀 *Merged From:* ${mergedBranch}
                    🔢 *Build:* #${env.BUILD_NUMBER}
                    🕒 *Time:* ${timeStamp}
                    📎 [View Build](${env.BUILD_URL})
                """.stripIndent().trim()

                sendTelegramMessage(message)
            }
        }

        failure {
            script {
                def gitBranch = sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
                def gitAuthor = sh(script: "git log -1 --pretty=format:%an", returnStdout: true).trim()
                def timeStamp = sh(script: "date '+%H:%M:%S %d-%m-%Y'", returnStdout: true).trim()

                def message = """
                    ❌ *FAILURE*

                    🚨 *Job:* ${env.JOB_NAME}
                    👤 *Author:* ${gitAuthor}
                    🌿 *Branch:* ${gitBranch}
                    🔢 *Build:* #${env.BUILD_NUMBER}
                    🕒 *Time:* ${timeStamp}
                    📎 [View Build](${env.BUILD_URL})
                """.stripIndent().trim()

                sendTelegramMessage(message)
            }
        }
    }
}

def sendTelegramMessage(message) {
    sh """
        curl -s -X POST "https://api.telegram.org/bot${env.TELEGRAM_BOT_TOKEN}/sendMessage" \\
        -d chat_id=${env.TELEGRAM_CHAT_ID} \\
        --data-urlencode text="${message}" \\
        -d parse_mode=Markdown
    """
}
