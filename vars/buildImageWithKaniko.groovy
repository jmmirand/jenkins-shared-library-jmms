import com.santander.ccc.build.Kaniko

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
