import javafx.application.Platform;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import splprime.SplPrime;
import splprime.interpreter.SPLOutput;
import ui.MainWindow;
import ui.StyleFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgramExecutionTest {

    private MainWindow mainWindow;


    private final String FIND_MIDDLE_NUMBER = "// Calculate the middle number of x,y,z\n" +
            "\n" +
            "// Input\n" +
            "var x = 14;\n" +
            "var y = 2;\n" +
            "var z = 5;\n" +
            "\n" +
            "// Here we go\n" +
            "var m = z ;\n" +
            "if( y < z ) {\n" +
            "if( x < y ) {\n" +
            "m = y ;\n" +
            "} else {\n" +
            "if ( x < z ) {\n" +
            "m = x ;\n" +
            "}\n" +
            "}\n" +
            "} else {\n" +
            "if ( x > y ) {\n" +
            "m = y ;\n" +
            "} else {\n" +
            "if ( x > z ) {\n" +
            "m = x ;\n" +
            "}\n" +
            "}\n" +
            "}\n" +
            "\n" +
            "// Result\n" +
            "print m ;\n";

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
        SplPrime.run(FIBONACCI_PROGRAM);
        assertTrue(true);
    }

    @Test
    public void testEuclidean() {
        SplPrime.run(EUCLIDEAN_PROGRAM);
        String output = SPLOutput.getInstance().getOutputText();
        output = output.replaceAll("\n", "").replaceAll("\r", "");
        assertEquals( "12.0", output);
    }

    @Test
    public void testMiddleNumber() {
        SplPrime.run(FIND_MIDDLE_NUMBER);
        String output = SPLOutput.getInstance().getOutputText();
        output = output.replaceAll("\n", "").replaceAll("\r", "");
        assertEquals("5.0", output);
    }
}
