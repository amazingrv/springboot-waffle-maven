pipeline {
  agent any
  stages {
    stage('Clean') {
      agent any
      steps {
        sh 'mvn clean'
      }
    }

    stage('Build') {
      steps {
        sh 'mvn package'
      }
    }

  }
}