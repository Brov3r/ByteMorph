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
