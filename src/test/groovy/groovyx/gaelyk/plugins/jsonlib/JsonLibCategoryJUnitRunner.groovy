package groovyx.gaelyk.plugins.jsonlib

import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement

class JsonLibCategoryJUnitRunner extends BlockJUnit4ClassRunner {
    public JsonLibCategoryJUnitRunner(Class klass) {
        super(klass);
    }

    protected Statement methodBlock(FrameworkMethod method) {
        def stmt = super.methodBlock(method);

        return {
            use(JsonLibCategory) {
                stmt.evaluate()
            }
        } as Statement
    }
}
