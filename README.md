# ByteMorph

A tool for dynamically modifying methods at runtime

# Example
For this tool to work successfully you must:
1) Install [JDK 17 or higher](https://www.oracle.com/java/technologies/downloads/).
2) In the launch script, specify the flag: `-Djdk.attach.allowAttachSelf=true`

```java
package com.brov3r.bytemorph.test;

/**
 * The {@link TestPrint} class contains a method that is intended to be modified
 * at runtime using the ByteMorph tool. Initially, the method simply prints a message
 * and returns {@code false}.
 */
public class TestPrint {
    /**
     * This method prints a message indicating that it has not been modified and returns {@code false}.
     * It is designed to be modified at runtime to demonstrate the capabilities of the ByteMorph tool.
     *
     * @return {@code false} always.
     */
    public static boolean printAndGetResult() {
        System.out.println("[X] The method has not been modified.");
        return false;
    }
}
```

```java
package com.brov3r.bytemorph.test;

import com.brov3r.bytemorph.AgentLoader;
import com.brov3r.bytemorph.AgentManager;
import javassist.*;

import java.io.IOException;

/**
 * The {@link ByteMorphTest} class is a demonstration of the ByteMorph tool's ability to modify
 * Java bytecode at runtime. This class shows how to dynamically change the behavior of a method
 * using the Javassist library.
 */
public class ByteMorphTest {
    /**
     * The entry point of the application.
     *
     * @param args Command-line arguments passed to the application.
     * @throws CannotCompileException If there is a compilation error while modifying the bytecode.
     * @throws IOException If an I/O error occurs.
     * @throws NotFoundException If the specified class or method is not found.
     */
    public static void main(String[] args) throws CannotCompileException, IOException, NotFoundException {
        // Retrieve the path to the ByteMorph agent JAR from the environment variable
        String agentPath = System.getenv("BYTEMORPH_JAR_PATH");

        // Print the path to the agent and the allowAttachSelf system property
        System.out.println("Path to Agent: " + agentPath);
        System.out.println("jdk.attach.allowAttachSelf = " + System.getProperty("jdk.attach.allowAttachSelf"));

        // Load the ByteMorph agent
        AgentLoader.loadAgent(agentPath);

        // Get the default ClassPool
        ClassPool defaultClassPool = ClassPool.getDefault();

        // Get the CtClass representation of the target class
        CtClass jndiLookupClass = defaultClassPool.get("com.brov3r.bytemorph.test.TestPrint");

        // Get the target method from the class
        CtMethod lookup = jndiLookupClass.getDeclaredMethod("printAndGetResult");

        // Modify the method body to print a message and return true
        lookup.setBody("{System.out.println(\"[#] The method was successfully modified.\"); return true;}");

        // Apply the modifications to the class
        AgentManager.modifyClass("com.brov3r.bytemorph.test.TestPrint", jndiLookupClass.toBytecode());

        // Verify if the method was successfully modified
        if (TestPrint.printAndGetResult()) {
            System.out.println("[#] ByteMorph successfully modified the method, test passed!");
        } else {
            System.out.println("[!] ByteMorph could not modify the method!");
        }
    }
}
```

# License

This project is licensed under [MIT License](./LICENSE).