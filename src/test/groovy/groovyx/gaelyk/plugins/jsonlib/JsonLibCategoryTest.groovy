package groovyx.gaelyk.plugins.jsonlib

import net.sf.json.JSONArray
import net.sf.json.JSONFunction
import net.sf.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

@RunWith(JsonLibCategoryJUnitRunner.class)
class JsonLibCategoryTest {
    private MockHttpServletRequest mockHttpServletRequest
    private MockHttpServletResponse mockHttpServletResponse

    @Before
    public void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest()
        mockHttpServletResponse = new MockHttpServletResponse()
    }

    @After
    public void tearDown() {
        mockHttpServletRequest = null
        mockHttpServletResponse = null
    }

    @Test
    void testParseJsonForRequestParameter() {
        mockHttpServletRequest.setParameter "json", "{\"name\":\"json\",\"bool\":true,\"int\":1,\"double\":2.2,\"func\":function(a){ return a; },\"array\":[1,2]}"
        Object object = mockHttpServletRequest.parseJson("json")
        assert "json" == object.name
        assert true == object.bool
        assert 1 == object.int
        assert 2.2d == object.double
        assert new JSONFunction(["a"] as String[], "return a;") == object.func
        def array = [1,2] as List
        assert array == object.array
    }

    @Test
    void testParseJsonForJsonString() {
        String jsonString = "{\"name\":\"json\",\"bool\":true,\"int\":1,\"double\":2.2,\"func\":function(a){ return a; },\"array\":[1,2]}"
        Object object = jsonString.parseJson()
        assert "json" == object.name
        assert true == object.bool
        assert 1 == object.int
        assert 2.2d == object.double
        assert new JSONFunction(["a"] as String[], "return a;") == object.func
        def array = [1,2] as List
        assert array == object.array
    }

    @Test
    void testRenderJsonForJSONObject() {
        def jsonObject = [integer:1, bool: true] as JSONObject
        mockHttpServletResponse.renderJson jsonObject
        assert "application/json" == mockHttpServletResponse.getContentType()
        assert "{\"integer\":1,\"bool\":true}" == mockHttpServletResponse.getContentAsString()
    }

    @Test
    void testRenderJsonForJSONArrayFromArray() {
        def jsonArray = JSONArray.fromObject([true,false,true] as boolean[])
        mockHttpServletResponse.renderJson jsonArray
        assert "application/json" == mockHttpServletResponse.getContentType()
        assert "[true,false,true]" == mockHttpServletResponse.getContentAsString()
    }

    @Test
    void testRenderJsonForJSONArrayFromList() {
        def jsonArray = JSONArray.fromObject([1,2,3])
        mockHttpServletResponse.renderJson jsonArray
        assert "application/json" == mockHttpServletResponse.getContentType()
        assert "[1,2,3]" == mockHttpServletResponse.getContentAsString()
    }

    @Test
    void testRenderJsonForObjectFromJavaBean() {
        def myBean = new MyBean()
        mockHttpServletResponse.renderJson myBean
        assert "application/json" == mockHttpServletResponse.getContentType()
        assert "{\"func1\":function(i){ return this.options[i]; },\"func2\":function(i){ return this.options[i]; },\"name\":\"json\",\"options\":[\"a\",\"f\"],\"pojoId\":1}" == mockHttpServletResponse.getContentAsString()
    }

    @Test
    void testRenderJsonForObjectFromMap() {
        def map = [:]
        map.put("name", "json")
        map.put("bool", Boolean.TRUE)
        map.put("int", new Integer(1))
        map.put("arr", ["a", "b"] as String[])
        map.put("func", "function(i){ return this.arr[i]; }")  
        mockHttpServletResponse.renderJson map
        assert "application/json" == mockHttpServletResponse.getContentType()
        assert "{\"name\":\"json\",\"bool\":true,\"int\":1,\"arr\":[\"a\",\"b\"],\"func\":function(i){ return this.arr[i]; }}" == mockHttpServletResponse.getContentAsString()
    }

    @Test
    void testRenderJsonForStringFromJsonString() {
        mockHttpServletResponse.renderJson "{\"name\":\"bla\"}"
        assert "application/json" == mockHttpServletResponse.getContentType()
        assert "{\"name\":\"bla\"}" == mockHttpServletResponse.getContentAsString()
    }

    class MyBean {
        String name = "json"
        int pojoId = 1
        char[] options = ['a','f'] as char[]
        String func1 = "function(i){ return this.options[i]; }"
        JSONFunction func2 = new JSONFunction(["i"] as String[],"return this.options[i];")
    }
}
