import groovyx.gaelyk.logging.GroovyLogger
import groovyx.gaelyk.plugins.jsonlib.JsonLibBuilderFactory
import groovyx.gaelyk.plugins.jsonlib.JsonLibCategory

def log = new GroovyLogger("jsonLibPlugin")
log.info "Registering JSON-lib plugin..."

binding {
    // Register JSON builder factory
    jsonLibBuilderFactory = JsonLibBuilderFactory.instance

    // Plugin library variables
    plugins = [
        json: [
            version: "0.2",
            lib: [
                name: "JSON-lib", 
                version: "2.4"
            ]
        ]
    ]
}

// Add JSON category
categories JsonLibCategory