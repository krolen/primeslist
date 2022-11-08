package my.test;

import lombok.extern.slf4j.Slf4j;
import my.test.impl.CachingPrimesFinder;
import my.test.impl.SimplePrimesFinder;
import my.test.impl.StepPrimesFinder;

import java.util.Random;

@Slf4j
public class Main {

    public static void main(String[] args) {

        log.info("Starting");
        int target = new RandomArgsParser().parseArgs(args);
        log.info("Finding primes up to {}", target);

        PrimesFinder simplePrimesFinder = new SimplePrimesFinder();
        log.info("Simple finder result: {}", simplePrimesFinder.findPrimesLessThan(target));

        PrimesFinder stepPrimesFinder = new StepPrimesFinder();
        log.info("Step finder result: {}", stepPrimesFinder.findPrimesLessThan(target));

        PrimesFinder cachedPrimesFinder = new CachingPrimesFinder(stepPrimesFinder);
        log.info("Cache finder result: {}", cachedPrimesFinder.findPrimesLessThan(target));
    }


    static class RandomArgsParser {
        int parseArgs(String[] args) {
            int argsLength = args.length;
            if (argsLength > 1) throw new IllegalArgumentException("No more than 1 argument is allowed");


            int result;
            if (argsLength == 0) {
                result = new Random().nextInt(1000);
            } else {
                double input;
                try {
                    input = Double.parseDouble(args[0]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Should be a number: " + args[0]);
                }
                result = (int) Math.floor(input);
                if (result <= 1) {
                    throw new IllegalArgumentException("Input value is either too big or less or equal to 1");
                }
            }
            return result;
        }
    }


}