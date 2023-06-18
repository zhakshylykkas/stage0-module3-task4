package school.mjc.stage0.module3.task4;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.PrimitiveType;
import org.junit.jupiter.api.Test;
import school.mjc.test.BaseIOTest;
import school.mjc.test.JavaFileSource;

import static com.github.javaparser.ast.type.PrimitiveType.Primitive.BOOLEAN;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.BYTE;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.CHAR;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.DOUBLE;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.FLOAT;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.LONG;
import static com.github.javaparser.ast.type.PrimitiveType.Primitive.SHORT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static school.mjc.parser.predicate.Dsl.declaration;

@JavaFileSource("src/main/java/school/mjc/stage0/module3/task4/SettingTypes.java")
class SettingTypesTest extends BaseIOTest {

    @Test
    void mainTestContainsCorrectTypes(CompilationUnit parsed) {
        assertVarWithTypeExists(parsed, LONG, "first");
        assertVarWithTypeExists(parsed, CHAR, "second");
        assertVarWithTypeExists(parsed, BOOLEAN, "third");
        assertVarWithTypeExists(parsed, DOUBLE, "forth");
        assertVarWithTypeExists(parsed, FLOAT, "fifth");
        assertVarWithTypeExists(parsed, BYTE, "sixth");
        assertVarWithTypeExists(parsed, SHORT, "seventh");
    }

    private void assertVarWithTypeExists(CompilationUnit parsed, PrimitiveType.Primitive type, String name) {
        int foundVars = parsed.findAll(VariableDeclarator.class,
            declaration(name).ofPrimitiveType(type)).size();
        assertEquals(1, foundVars,
            "Variable " + name + " was removed or doesn't have correct type");
    }
}
