import jmms.kaniko

def call(script,
         dockerRegistry = null,
         dockerfileRepo = null,
         dockerBuildParams = null) {

    def kaniko = new Kaniko(script)
    kaniko.buildCustomImage(
        dockerRegistry,
        dockerfileRepo,
        dockerBuildParams
    )
}
