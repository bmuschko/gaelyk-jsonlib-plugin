package groovyx.gaelyk.plugins.jsonlib

import org.junit.Test

/**
 * Created by IntelliJ IDEA.
 * User: ben
 * Date: Nov 7, 2010
 * Time: 8:06:23 PM
 * To change this template use File | Settings | File Templates.
 */
class JsonLibBuilderFactoryTest {
    @Test
    void testGetJsonLibBuilder() {
        def jsonLibBuilder = JsonLibBuilderFactory.instance.getJsonLibBuilder()
        assert jsonLibBuilder instanceof JsonLibGroovyBuilder == true
    }
}
