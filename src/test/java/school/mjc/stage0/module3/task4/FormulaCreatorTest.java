package school.mjc.stage0.module3.task4;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.type.PrimitiveType;
import org.junit.jupiter.api.Test;
import school.mjc.test.BaseIOTest;
import school.mjc.test.JavaFileSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.github.javaparser.ast.expr.BinaryExpr.Operator.DIVIDE;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.MINUS;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.MULTIPLY;
import static com.github.javaparser.ast.expr.BinaryExpr.Operator.PLUS;
import static org.junit.jupiter.api.Assertions.*;
import static school.mjc.parser.predicate.Dsl.binaryExpression;
import static school.mjc.parser.predicate.Dsl.binaryExpressionNotStrict;
import static school.mjc.parser.predicate.Dsl.intLiteral;
import static school.mjc.parser.predicate.Dsl.variableRef;

@JavaFileSource("src/main/java/school/mjc/stage0/module3/task4/FormulaCreator.java")
class FormulaCreatorTest extends BaseIOTest {

    @Test
    void mainContainsFormula(CompilationUnit parsed) {

        Predicate<Expression> powerPredicate = expression -> {
            // go into brackets
            while (expression.isEnclosedExpr()) {
                expression = expression.asEnclosedExpr().getInner();
            }
            if (expression.isBinaryExpr()) {
                // check a*a part
                BinaryExpr binaryExpr = expression.asBinaryExpr();
                return binaryExpression(variableRef("a"), MULTIPLY, variableRef("a"))
                    .checkBinaryExpression(binaryExpr);
            } else if (expression.isCastExpr()) {
                CastExpr castExpr = expression.asCastExpr();
                if (!castExpr.getType().isPrimitiveType() ||
                    castExpr.getType().asPrimitiveType().getType() != PrimitiveType.Primitive.INT) {
                    return false;
                }
                if (!castExpr.getExpression().isMethodCallExpr()) {
                    return false;
                }
                MethodCallExpr methodCallExpr = castExpr.getExpression().asMethodCallExpr();
                return methodCallExpr.getName().getIdentifier().equals("pow") &&
                    methodCallExpr.getArguments().size() == 2 &&
                    variableRef("a").test(methodCallExpr.getArguments().get(0)) &&
                    intLiteral(2).test(methodCallExpr.getArguments().get(1));
            }
            return false;
        };

        Predicate<Expression> leftPredicate = binaryExpressionNotStrict(
            binaryExpressionNotStrict(
                binaryExpressionNotStrict(
                    binaryExpressionNotStrict(
                        binaryExpressionNotStrict(
                            intLiteral(9), MULTIPLY, powerPredicate
                        ).or(
                            binaryExpressionNotStrict(
                                binaryExpressionNotStrict(
                                    intLiteral(9), MULTIPLY, variableRef("a")
                                ),
                                MULTIPLY,
                                variableRef("a")
                            )
                        ),
                        MINUS,
                        binaryExpressionNotStrict(
                            intLiteral(5), MULTIPLY, variableRef("b")
                        )
                    ),
                    PLUS,
                    intLiteral(2)
                ),
                PLUS,
                variableRef("a")
            ),
            MINUS,
            intLiteral(7)
        );

        Predicate<Expression> rightPredicate = binaryExpressionNotStrict(
            binaryExpressionNotStrict(
                binaryExpressionNotStrict(
                    variableRef("a"), PLUS, variableRef("b")
                ),
                MINUS,
                binaryExpressionNotStrict(
                    binaryExpressionNotStrict(
                        intLiteral(4), MULTIPLY, variableRef("a")
                    ),
                    MULTIPLY,
                    variableRef("b")
                )
            ),
            DIVIDE,
            intLiteral(2)
        );


        boolean formulaFound = parsed.findAll(Expression.class,
            binaryExpressionNotStrict(leftPredicate, MULTIPLY, rightPredicate)
        ).size() == 1;

        assertTrue(formulaFound, "Couldn't find correct formula implementation. " +
            "Please don't change the order of operations");
    }
}
