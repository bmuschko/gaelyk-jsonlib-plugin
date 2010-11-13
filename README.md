# Gaelyk plugin providing JSON support via JSON-lib

The plugin uses the [JSON-lib](http://json-lib.sourceforge.net/) libraries to provided JSON support in Gaelyk plugins.
Additionally it add syntactic sugar on top of the Gaelyk APIs.

## Installation

To use the plugin in your Gaelyk application extract the distribution into your project directory. You should end up
with multiple JAR files in your `war/WEB-INF/lib` and a plugin descriptor named `jsonLibPlugin.groovy` under `war/WEB-INF/plugins`.

If you haven't created the file `war/WEB-INF/plugins.groovy` already please create it and add the following line to it:

    install jsonLibPlugin

## Usage

Unfortunately, the `JsonGroovyBuilder` provided by JSON-lib has a [bug](http://sourceforge.net/tracker/?func=detail&aid=3022114&group_id=171425&atid=857928)
which prevents it from building the JSON correctly from a Groovy script. This bug hasn't been fixed yet but the plugin
provides the fix for it. You can access the [Groovy JSON builder](http://json-lib.sourceforge.net/groovy.html) in your Groovy templates and Groovlet
by using the factory for it:

    jsonLibBuilderFactory.jsonLibBuilder

The following example shows the builder the action. Please stick to the [JavaDoc documentation](http://json-lib.sourceforge.net/apidocs/jdk15/net/sf/json/groovy/JsonGroovyBuilder.html)
for more information.

    def record = datastore.get(key)

    def jsonGoal = jsonLibBuilderFactory.jsonLibBuilder.json {
        name = record.name
        description = record.description
        created = record.created.format("yyyy/MM/dd")
    }

The plugin provides additional methods to the HttpServletRequest and HttpServletResponse objects. If you want to parse a
request parameter and convert it to an object.

    // json = {"name":"json","bool":true,"int":1,"double":2.2,"func":function(a){ return a; },"array":[1,2]}
    Object object = request.parseJson("json")
    assert "json" == object.name
    assert true == object.bool
    assert 1 == object.int
    assert 2.2d == object.double
    assert new JSONFunction(["a"] as String[], "return a;") == object.func
    def array = [1,2] as List
    assert array == object.array

You can apply the same method name on a JSON String for example if you called a third-party API that responded with a JSON
String.

    // json = {"name":"json","bool":true,"int":1,"double":2.2,"func":function(a){ return a; },"array":[1,2]}
    Object object = jsonString.parseJson()
    assert "json" == object.name
    assert true == object.bool
    assert 1 == object.int
    assert 2.2d == object.double
    assert new JSONFunction(["a"] as String[], "return a;") == object.func
    def array = [1,2] as List
    assert array == object.array

To render JSON in your response you can use the method `renderJson` on the HttpServletResponse object. As parameters you
can use `JSONObject`, `JSONArray`, an object or a String. If you wish to change the encoding you can do so by adding it as
second parameter. The default encoding is UTF-8. The content type for the response will automatically be set to
`application/json`.

    // Generated response = {"integer":1,"bool":true}
    def jsonObject = [integer:1, bool: true] as JSONObject
    response.renderJson jsonObject

    // Generated response = [1,2,3]
    def jsonArray = JSONArray.fromObject([true,false,true] as boolean[])
    response.renderJson jsonArray

    // Generated response = {"name":"json","bool":true,"int":1,"arr":["a","b"]}
    def map = [:]
    map.put("name", "json")
    map.put("bool", Boolean.TRUE)
    map.put("int", new Integer(1))
    map.put("arr", ["a", "b"] as String[])
    response.renderJson map

    // Generated response = {"name":"json","pojoId":1,"options":["a","f"]}
    def myBean = new MyBean()
    response.renderJson myBean

    class MyBean {
        String name = "json"
        int pojoId = 1
        char[] options = ['a','f'] as char[]
    }