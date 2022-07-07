package lang.print.gaps.task4;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FormulaCreatorTest {

    @Test
    void mainContainsFormula() throws IOException {
        Path path = Paths.get("src/main/java/lang/print/gaps/task4/FormulaCreator.java");
        List<String> strings = Files.readAllLines(path);
        List<String> castedRows = strings.stream()
                .filter(line ->
                        line.contains("(9 * Math.pow(a, 2) - 5 * b + 2 + a - 7) * ((a + b - 4 * a * b) / 2)") ||
                        line.contains("(9 * Math.pow(a, 2) - 5 * b + 2 + a - 7) * (a + b - 4 * a * b) / 2") ||
                        line.contains("(9 * a * a - 5 * b + 2 + a - 7) * ((a + b - 4 * a * b) / 2)") ||
                        line.contains("(9 * (a ^ 2) - 5 * b + 2 + a - 7) * ((a + b - 4 * a * b) / 2)") ||
                        line.contains("(9 * (a * a) - 5 * b + 2 + a - 7) * ((a + b - 4 * a * b) / 2)") ||
                        line.contains("(9*Math.pow(a, 2) - 5*b + 2 + a - 7) * ((a + b - 4*a*b) / 2)") ||
                        line.contains("(9 * a * a - 5 * b + 2 + a - 7) * (a + b - 4 * a * b) / 2)"))
                .collect(Collectors.toList());

        assertEquals(1, castedRows.size());
    }
}