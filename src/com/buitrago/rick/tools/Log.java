package com.buitrago.rick.tools;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public class Log {
    public final static int NANO_TO_MILLIS = 1000;

    /**
     * Executes {@code integerFunction} and measures time elapsed of its execution; prints results using {@code formattingFunction}.
     *
     * @param number the number to have the {@code integerFunction} applied to
     * @param integerFunction the function to apply to {@code number}
     * @param formattingFunction the function used to print the output of the {@code integerFunction}
     * @return the elapsed time measured while executing {@code integerFunction}
     * */
    public static int executeAndMeasureElapsedTime(
            int number,
            Function<Integer, Integer> integerFunction,
            Function<int[], String> formattingFunction) {
        Instant before = Instant.now();
        int result = integerFunction.apply(number);
        Instant after = Instant.now();

        final int elapsed = Duration.between(before, after).getNano() / NANO_TO_MILLIS;
        System.out.println(formattingFunction.apply(new int[]{elapsed, number, result}));

        return elapsed;
    }
}
