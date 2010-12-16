package groovyx.gaelyk.plugins.jsonlib

import net.sf.json.groovy.JsonGroovyBuilder
import org.codehaus.groovy.control.CompilerConfiguration
import org.junit.Test

class JsonLibBuilderFactoryTest {
    @Test
    void testGetJsonLibBuilder() {
        def jsonLibBuilder = JsonLibBuilderFactory.instance.getJsonLibBuilder()
        assert jsonLibBuilder instanceof JsonGroovyBuilder == true
    }

    @Test
    void testInvokeJsonLibBuilderFromScript() {
        def binding = new Binding()
        def config = new CompilerConfiguration()
        def file = new File("src/test/groovy/script/JsonBuilderUsage.groovy")

        Script script = new GroovyShell(binding, config).parse(file)
        def result = script.run()
        assert "Travel the world" == result.name
        assert "in 80 days around the world" == result.description
        assert new Date().format("yyyy/MM/dd") == "2010/12/16"
    }
}
