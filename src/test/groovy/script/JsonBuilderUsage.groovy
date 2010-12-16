import groovyx.gaelyk.plugins.jsonlib.JsonLibBuilderFactory

def jsonLibBuilder = JsonLibBuilderFactory.instance.getJsonLibBuilder()

jsonLibBuilder.json {
    name = "Travel the world"
    description = "in 80 days around the world"
    created = new Date().format("yyyy/MM/dd")
}