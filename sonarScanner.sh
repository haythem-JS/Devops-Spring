mvn clean verify sonar:sonar \
  -Dsonar.projectKey=Devops_spring \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=admin