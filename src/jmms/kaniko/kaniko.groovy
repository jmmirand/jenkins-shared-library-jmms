package com.santander.ccc.build

import groovy.json.JsonOutput

class Kaniko {
    def script
    def steps
    def env

    def Kaniko(script) {
        this.script = script
        this.steps = script.steps
        this.env = script.env
    }


    def buildCustomImage(dockerRegistry, dockerfileRepo, dockerBuildParams) {
        if (dockerfileRepo) {
            script.echo "Downloading Dockerfile repo"
            script.git url: "${dockerfileRepo}"
        } else {
            script.echo "[WARNING] dockerfileRepo parameter is empty. Project Dockerfile will be used"
        }

        def KANIKO_DEBUG = (env.LOG_LEVEL == 'DEBUG') ? '-v debug' : ''
        def executorCommand = "#!/busybox/sh\n /kaniko/executor ${KANIKO_DEBUG}" +
            " --context `pwd` --skip-tls-verify" +
            " --skip-tls-verify-registry ${dockerRegistry.url}" +
            " --destination=${dockerRegistry.url}/${dockerRegistry.image}"

        if (dockerBuildParams) {
            dockerBuildParams.each { key, value ->
                executorCommand = executorCommand + ' --build-arg ' + key + '=' + value
            }
        }

        def dockerConfig = buildDockerConfig(dockerRegistry)
        def createDockerConfigResult = script.sh(
            script: "#!/busybox/sh\n echo '${JsonOutput.toJson(dockerConfig)}' > /kaniko/.docker/config.json",
            returnStatus: true
        )
        script.echo("createDockerConfigResult: ${createDockerConfigResult}")

        script.echo "executorCommand: ${executorCommand}"
        def executorCommandResult = script.sh(
            script: executorCommand,
            returnStatus: true
        )
        script.echo("executorCommandResult: ${executorCommandResult}")
    }

    def buildDockerConfig(dockerRegistry) {
        return [
            "auths": [
                "${dockerRegistry.url}" : [
                    "username" : "${dockerRegistry.username}",
                    "password" : "${dockerRegistry.password}",
                    "email" : "${dockerRegistry.email}"
                ]
            ]
        ]
    }
∫}
