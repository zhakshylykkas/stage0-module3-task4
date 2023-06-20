package school.mjc.stage0.module3.task4;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import org.junit.jupiter.api.Test;
import school.mjc.test.BaseIOTest;
import school.mjc.test.JavaFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JavaFileSource("src/main/java/school/mjc/stage0/module3/task4/IntRepresentation.java")
class IntRepresentationTest extends BaseIOTest {

    @Test
    void testOutput() {
        IntRepresentation.main(null);

        assertOutputIgnoreLineDelimiters("char");
    }

    @Test
    void mainPrintToConsoleExpected(CompilationUnit parsed) {
        int charC = parsed.findAll(IntegerLiteralExpr.class,
            il -> il.getValue().equals(((int)'c') + "")).size();
        int charH = parsed.findAll(IntegerLiteralExpr.class,
            il -> il.getValue().equals(((int)'h') + "")).size();
        int charA = parsed.findAll(IntegerLiteralExpr.class,
            il -> il.getValue().equals(((int)'a') + "")).size();
        int charR = parsed.findAll(IntegerLiteralExpr.class,
            il -> il.getValue().equals(((int)'r') + "")).size();
        assertEquals(1, charC, "Expected to find exactly one 'c' char representation");
        assertEquals(1, charH, "Expected to find exactly one 'h' char representation");
        assertEquals(1, charA, "Expected to find exactly one 'a' char representation");
        assertEquals(1, charR, "Expected to find exactly one 'r' char representation");

        assertEquals(0, parsed.findAll(CharLiteralExpr.class).size(),
            "Please don't use char literals");
        assertEquals(0, parsed.findAll(StringLiteralExpr.class).size(),
            "Please don't use string literals");
    }
}
