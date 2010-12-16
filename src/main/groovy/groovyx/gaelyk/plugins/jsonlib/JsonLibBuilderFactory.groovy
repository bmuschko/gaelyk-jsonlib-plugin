package groovyx.gaelyk.plugins.jsonlib

import net.sf.json.groovy.JsonGroovyBuilder

@Singleton
class JsonLibBuilderFactory {
    def getJsonLibBuilder() {
        new JsonGroovyBuilder()
    }
}
