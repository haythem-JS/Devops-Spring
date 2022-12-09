
def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t haythem69/demo-devops-spring:spring-app-${IMAGE_NAME} ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push haythem69/demo-devops-spring:spring-app-${IMAGE_NAME}"
    }
}

def pushToNexus() {
    echo "pushing the jar file to Nexus maven-snapshots repo..."
    sh 'mvn clean deploy `-Dmaven.test.skip=true'
}

def sonarScan(String serverIp, String serverUser) {
    echo "Running sonarQube scan..."
    sh "set MYSQLDB_ROOT_PASSWORD root"
    sh "set  MYSQLDB_DATABASE school"
    sh "set MYSQLDB_LOCAL_PORT 3306"
    sh "set MYSQLDB_DOCKER_PORT 3306"
    sh 'mvn sonar:sonar `-Dsonar.projectKey=Devops_spring `-Dsonar.host.url=http://localhost:9000/ `-Dsonar.login=beb2521919d74495ecd0a0961d9bd5b37c2458d4'
//    sshagent (credentials: ['sonar-server']) {
//        sh "ssh -o StrictHostKeyChecking=no ${serverUser}@${serverIp} ${runSonar}"
//    }
}

def deployApp(String serverIp, String serverUser) {
    echo 'deploying the application...'
    sh "export MYSQLDB_USER=root MYSQLDB_ROOT_PASSWORD=root MYSQLDB_DATABASE=school MYSQLDB_LOCAL_PORT=3306 MYSQLDB_DOCKER_PORT=3306 SPRING_LOCAL_PORT=8080 SPRING_DOCKER_PORT=8080 && docker-compose up -d"
//    sshagent (credentials: ['deployment-server']) {
//        sh "ssh -o StrictHostKeyChecking=no ${serverUser}@${serverIp} ${composeRun}"
//    }
}

def cleanUntaggedImages(String serverIp, String serverUser){
    sh 'docker image prune --force --filter "dangling=true"'
//    sshagent (credentials: ['jenkins-server']) {
//        sh "ssh -o StrictHostKeyChecking=no ${serverUser}@${serverIp} ${cleanImages}"
//    }
}

return this
