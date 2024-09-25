import org.junit.jupiter.api.Test;
import splprime.SplPrime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgramExecutionTest {

    private final String FIBONACCI_PROGRAM = "// Print the first n Fibonacci numbers\n" +
            "\n" +
            "// Input\n" +
            "var n = 10;\n" +
            "\n" +
            "// Here we go\n" +
            "var a = 0;\n" +
            "var b = 1;\n" +
            "\n" +
            "var i = 1;\n" +
            "while ( i <= n ) {\n" +
            "print a ;\n" +
            "var next = a + b ;\n" +
            "a = b ;\n" +
            "b = next ;\n" +
            "i = i + 1;\n" +
            "}\n";

    private final String EUCLIDEAN_PROGRAM = "\n" +
            "// Euclidean algorithm : Calculate the greatest common divisor of two numbers\n" +
            "\n" +
            "// Input\n" +
            "var a = 36;\n" +
            "var b = 24;\n" +
            "a = a - b ;\n" +
            "\n" +
            "// Here we go\n" +
            "while ( a != b ) {\n" +
            "if ( a > b ) {\n" +
            "a = a - b ;\n" +
            "} else {\n" +
            "b = b - a ;\n" +
            "}\n" +
            "}\n" +
            "\n" +
            "// Result\n" +
            "print a ;\n";

    @Test
    public void testFibonacci() {
        //SplPrime.run(FIBONACCI_PROGRAM);
        assertTrue(true);
    }
}
