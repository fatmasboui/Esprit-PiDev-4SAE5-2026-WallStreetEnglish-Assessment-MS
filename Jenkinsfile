pipeline {
    agent any

    environment {
        // Définir les variables d'environnement nécessaires
        SONAR_TOKEN = credentials('sonar-cloud-token') // Assurez-vous d'avoir ce credential dans Jenkins
        DOCKER_REGISTRY = "docker.io/votre-utilisateur" // Remplacez par votre utilisateur Docker Hub
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "Récupération du code source..."
                    checkout scm
                }
            }
        }

        stage('Unit Tests & Quality') {
            parallel {
                stage('Assessment Service') {
                    steps {
                        dir('assessment-service') {
                            sh 'mvn clean verify sonar:sonar -Dsonar.token=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Quiz Service') {
                    steps {
                        dir('quiz-service') {
                            sh 'mvn clean verify sonar:sonar -Dsonar.token=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Career Service') {
                    steps {
                        dir('career-service') {
                            sh 'mvn clean verify sonar:sonar -Dsonar.token=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Certification Service') {
                    steps {
                        dir('certification-service') {
                            sh 'mvn clean verify sonar:sonar -Dsonar.token=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Notification Service') {
                    steps {
                        dir('notification-service') {
                            sh 'mvn clean verify sonar:sonar -Dsonar.token=${SONAR_TOKEN}'
                        }
                    }
                }
                stage('Infrastructure Services') {
                    steps {
                        script {
                            dir('eureka-server') {
                                sh 'mvn clean package -DskipTests'
                            }
                            dir('api-gateway') {
                                sh 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    echo "Construction des images Docker..."
                    sh 'docker-compose build'
                }
            }
        }

        stage('Push to Registry') {
            steps {
                script {
                    echo "Poussée des images vers le registre (Optionnel)..."
                    // sh 'docker-compose push'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "Déploiement des microservices..."
                    sh 'docker-compose up -d'
                }
            }
        }
    }

    post {
        always {
            echo "Nettoyage de l'espace de travail..."
            // cleanWs()
        }
        success {
            echo "Pipeline terminé avec succès !"
        }
        failure {
            echo "Le pipeline a échoué. Veuillez vérifier les logs."
        }
    }
}
