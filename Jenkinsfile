pipeline {
	agent {
		label 'migration'
	}
	
	options {
		buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
		timestamps()
	}
	
	tools {
		maven 'apache-maven-latest'
		jdk 'temurin-jdk17-latest'
	}
	
	environment {
		// Target platform to build against (must correspond to a profile in the parent pom.xml)
		PLATFORM = 'platform-2024-03'
	}
	
	stages {
		stage ('Nightly') {
			when {
				allOf {
					not {
						branch 'PR-*'
					}
					not {
						tag "*"
					}
				}
			}
			steps {
				wrap([$class: 'Xvnc', takeScreenshot: false, useXauthority: true]) {
					sh "mvn clean deploy -P$PLATFORM -DbuildQualifier=`date +%Y%m%d%H%M` -Psign"
					sh "mvn clean deploy -f releng/maven/pom.xml"
				}
				sshagent ( ['projects-storage.eclipse.org-bot-ssh']) {
					sh '''
						chmod +x ./releng/org.eclipse.acceleo.releng/publish-nightly.sh
						./releng/org.eclipse.acceleo.releng/publish-nightly.sh
					'''
				}
			}
		}
		stage ('Tag') {
			when {
				allOf {
					not {
						branch 'PR-*'
					}
					tag "*"
				}
			}
			steps {
				wrap([$class: 'Xvnc', takeScreenshot: false, useXauthority: true]) {
					sh "mvn clean verify deploy -P$PLATFORM -DbuildQualifier=`date +%Y%m%d%H%M` -Psign"
					sh "mvn clean deploy -f releng/maven/pom.xml"
				}
				sshagent ( ['projects-storage.eclipse.org-bot-ssh']) {
					sh '''
						chmod +x ./releng/org.eclipse.acceleo.releng/publish-nightly.sh
						./releng/org.eclipse.acceleo.releng/publish-nightly.sh
					'''
				}
			}
		}
		stage ('PR Verify') {
			when {
				branch 'PR-*'
			}
			steps {
				wrap([$class: 'Xvnc', takeScreenshot: false, useXauthority: true]) {
					sh "mvn clean verify -P$PLATFORM -DbuildQualifier=`date +%Y%m%d%H%M`"
					sh "mvn clean deploy -f releng/maven/pom.xml"
				}
			}
		}
	}
	
	post {
		always {
			junit "tests/*.test*/target/surefire-reports/*.xml,query/tests/*.test*/target/surefire-reports/*.xml"
		}
		unsuccessful {
			emailext (
				subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
				body: """FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':
				Check console output at ${env.BUILD_URL}""",
				to: 'laurent.goubet@obeo.fr, yvan.lussaud@obeo.fr'
			)
		}
	}
}
