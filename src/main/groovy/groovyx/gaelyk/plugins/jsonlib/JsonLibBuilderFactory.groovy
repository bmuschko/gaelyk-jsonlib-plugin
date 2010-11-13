package groovyx.gaelyk.plugins.jsonlib

@Singleton
class JsonLibBuilderFactory {
    def getJsonLibBuilder() {
        new JsonLibGroovyBuilder()
    }
}
