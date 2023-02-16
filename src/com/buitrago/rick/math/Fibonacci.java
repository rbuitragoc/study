package com.buitrago.rick.math;

import com.buitrago.rick.tools.Log;

import java.util.function.Function;

public class Fibonacci {

    private final static String FUNCTION_DEFINITION = "Let f(n) be a function such that it calculates the fibonacci value for an integer n.";
    private final static String FUNCTION_ARGUMENTS_FORMAT_EXPRESSION = "(received via argument: n = %d)";
    private final static String FUNCTION_SEQUENCE_FORMAT_EXPRESSION = "Printing below the sequence in the range [0..n] up to f(%d):";
    private final static String FUNCTION_NUMBER_FIBONACCI_EXPRESSION = "Printing below fibonacci number for %d";
    private final static String ELAPSED_TIME_EXPRESSION = "Elapsed time: %d ms";
    private final static String STEP_OUTPUT_FORMAT_EXPRESSION = "f(%d) = %d";
    private final static String NEW_LINE = "\n";
    private final static String DASH = " - ";

    private String formatHeader(int n) {
        final String ARGUMENTS_SUBSTRING = String.format(FUNCTION_ARGUMENTS_FORMAT_EXPRESSION, n);
        return String.join(NEW_LINE, FUNCTION_DEFINITION, ARGUMENTS_SUBSTRING);
    }

    private String formatHeaderAndFibonacciSequence(int n) {
        final String SEQUENCE_SUBSTRING = String.format(FUNCTION_SEQUENCE_FORMAT_EXPRESSION, n);
        return String.join(NEW_LINE, formatHeader(n), SEQUENCE_SUBSTRING);
    }

    private String formatHeaderAndFibonacciNumber(int n) {
        final String NUMBER_SUBSTRING = String.format(FUNCTION_NUMBER_FIBONACCI_EXPRESSION, n);
        return String.join(NEW_LINE, formatHeader(n), NUMBER_SUBSTRING);
    }

    /**
     * @param params The following order and values are expected:
     * <ol>
     *               <li>{@code [0]} elapsed time in nanos</li>
     *               <li>{@code [1]} number for which the function will be applied</li>
     *               <li>{@code [2]} result number of applying the function</li>
     * </ol>*/
    private String formatElapsedTimeAndStep(int ... params) {
        final String ELAPSED_SUBSTRING = String.format(ELAPSED_TIME_EXPRESSION, params[0]);
        final String STEP_SUBSTRING = String.format(STEP_OUTPUT_FORMAT_EXPRESSION, params[1], params[2]);
        return String.join(DASH, ELAPSED_SUBSTRING, STEP_SUBSTRING);
    }

    /**
     * Prints Fibonacci sequence, and returns the elapsed time
     * <p/>
     * The method defines the fibonacci function as {@code f(n)} and prints a step in the sequence
     * as an evaluation of the function at the integer value of every step.
     * <p/>
     * Conversely, every step is any integer in the range {@code [0..n]} (inclusive).
     *
     * @param i an integer for which the Fibonacci sequence will be printed.
     *
     * @return elapsed time of last step in milliseconds, as per {@link Log#executeAndMeasureElapsedTime(int, Function, Function)}
     * */
    public int printFibonacciSequence(int i) {
        System.out.println(formatHeaderAndFibonacciSequence(i));
        int elapsed = 0;
        for (int j = 0; j <= i; j++) {
            elapsed = Log.executeAndMeasureElapsedTime(j, this::fibonacci, this::formatElapsedTimeAndStep);
        }
        return elapsed;
    }

    /**
     * Prints Fibonacci number, and returns the elapsed time.
     * <p/>
     * The method defines the fibonacci function as {@code f(n)} and prints the calculation of the number for the value
     * passed as an argument
     *
     * @param i an integer for which fibonacci will be calculated
     * @return elapsed time
     * */
    public int printFibonacciNumber(int i) {
        System.out.println(formatHeaderAndFibonacciNumber(i));
        return Log.executeAndMeasureElapsedTime(i, this::fibonacci, this::formatElapsedTimeAndStep);
    }
    /**
     * Calculates Fibonacci number for an integer i.
     *
     * @param i an integer passed as an argument.
     * @return the fibonacci number calculated for {@code i}. If {@code i <= 0}, the method will return {@code 0}. Otherwise,
     * the method will return the addition of two numbers via recursive calls (RC):
     * <ul>
     *     <li>{@code fibonacci(i - 1)}</li>
     *     <li>{@code fibonacci(i - 2)}</li>
     * </ul>
     * */
    public int fibonacci(int i) {
        return i <= 0 ? 0 : (i == 1 ? 1 : fibonacci(i - 1) + fibonacci(i - 2));
    }

    private static void testRecursiveFibonacciAndCompareLastStepInSequenceAgainstNormalCalculationFor(int number) {
        Fibonacci f = new Fibonacci();
        int elapsedSequence = f.printFibonacciSequence(number);
        System.out.println(">> That's taking just too long. Trying now only the Fibonacci number instead");
        System.out.println(String.format(">> Expecting now elapsed time slightly above last step in sequence - was %d ms", elapsedSequence));
        System.out.println(">> fingers crossed 🤞");
        int elapsedNumber = f.printFibonacciNumber(number);
        int differenceInDuration = elapsedNumber - elapsedSequence;
        final String SPEED_SUBSTRING = differenceInDuration > 0 ? "FASTER" : "SLOWER";
        final String CONCLUSION_CONDITIONAL = String.format("That means calculating an isolated step is actually %s than outputting the number without step output.", SPEED_SUBSTRING);
        final String CONCLUSION_EXPRESSION = ">> CONCLUSION: %d for just the Fibonacci number, %d for the last step in the sequence, that's %d difference. %s";
        final String CONCLUSION_SUBSTRING = String.format(CONCLUSION_EXPRESSION, elapsedNumber, elapsedSequence, differenceInDuration, CONCLUSION_CONDITIONAL);
        System.out.println(CONCLUSION_SUBSTRING);
    }

    public static void main(String[] args) {
        testRecursiveFibonacciAndCompareLastStepInSequenceAgainstNormalCalculationFor(50);
    }
}
