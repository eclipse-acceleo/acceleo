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
		jdk 'temurin-jdk25-latest'
	}
	
	environment {
		// Target platforms and profiles to build against (must correspond to a profile in the parent pom.xml)
		PROFILES = 'platform-2026-09,updatesite'
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
					sh "mvn clean deploy -P$PROFILES -DbuildQualifier=`date +%Y%m%d%H%M` -Psign -Djdk.xml.maxGeneralEntitySizeLimit=0 -Djdk.xml.totalEntitySizeLimit=0"
					sh "mvn clean deploy -f releng/maven/pom.xml"
					sh "mvn clean verify -f tests/maven/pom.xml"
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
					sh "mvn clean deploy -P$PROFILES -DbuildQualifier=`date +%Y%m%d%H%M` -Psign -Djdk.xml.maxGeneralEntitySizeLimit=0 -Djdk.xml.totalEntitySizeLimit=0"
					sh "mvn clean deploy -f releng/maven/pom.xml"
					sh "mvn clean verify -f tests/maven/pom.xml"
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
					sh "mvn clean deploy -P$PROFILES -DbuildQualifier=`date +%Y%m%d%H%M` -Djdk.xml.maxGeneralEntitySizeLimit=0 -Djdk.xml.totalEntitySizeLimit=0"
					sh "mvn clean deploy -f releng/maven/pom.xml"
					sh "mvn clean verify -f tests/maven/pom.xml"
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
