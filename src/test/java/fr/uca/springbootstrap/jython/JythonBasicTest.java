package fr.uca.springbootstrap.jython;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.python.util.PythonInterpreter;

import java.io.StringWriter;
import java.util.Arrays;

public class JythonBasicTest {
    @Test
    public void helloWorld() {
        PythonInterpreter pyInterp = new PythonInterpreter();
        StringWriter output = new StringWriter();
        pyInterp.setOut(output);

        int[] inputs = new int[]{0,2,3,6,10};
        int[] outputs = new int[]{0,4,9,36,100};

        try {
            for (int i : inputs) {
                pyInterp.set("runner_input", i);
                pyInterp.exec("""
                        def square(value):\n
                            return value*value\n
                        print(square(runner_input))
                        """);
            }
        }catch (Exception e){
            System.out.println("Wrong code");
        }
        Assertions.assertArrayEquals(Arrays.stream(outputs).mapToObj(String::valueOf).toArray(String[]::new),output.toString().split("\n"));
    }
}
