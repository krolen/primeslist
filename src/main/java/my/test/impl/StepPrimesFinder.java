package my.test.impl;

import my.test.PrimeChecker;
import my.test.PrimesFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A bit more advanced one: given a prime in constructor it creates
 * a step which is a multiplication of all primes up to prime (inclusive).
 * after that instead of checking prime-ness of each number it checks prime-ness with the step and considers
 * numbers in a format (step + k) where k has no prime dividers up to prime parameter
 */
public class StepPrimesFinder implements PrimesFinder {

    private final int step;
    private final List<Integer> stepCandidates;
    private final List<Integer> bufferPrimes;
    private final int buffer;
    private final PrimeChecker primeChecker;

    public StepPrimesFinder() {
        this(17);
    }

    public StepPrimesFinder(int prime) {
        this.buffer = prime;
        if (prime < 2) throw new IllegalArgumentException("Buffer should be more than 1");
        if (prime > 17)
            throw new IllegalArgumentException("Please consider prime no more than 13 to avoid long initial calculations and prime overflow");
        primeChecker = new PrimeChecker(5);

        SimplePrimesFinder simplePrimesFinder = new SimplePrimesFinder();
        bufferPrimes = simplePrimesFinder.findPrimesLessThan(prime);
        if (!primeChecker.isPrime(prime, bufferPrimes)) {
            throw new IllegalArgumentException("Input parameter is not prime");
        }

        step = bufferPrimes.stream().reduce(1, (i1, i2) -> i1 * i2);
        if (step < 0) throw new IllegalArgumentException("Buffer value is too big, please choose a smaller one");

        List<Integer> stepPrimes = simplePrimesFinder.findPrimesLessThan(step);
        stepCandidates = new ArrayList<>();
        stepCandidates.add(1);
        for (int i = bufferPrimes.size(); i < stepPrimes.size(); i++) {
            stepCandidates.add(stepPrimes.get(i));
        }

    }

    @Override
    public List<Integer> findPrimesLessThan(int target) {
        if (target < 1) throw new IllegalArgumentException("Argument should be more than 1");

        if (target <= buffer) {
            return bufferPrimes.stream().filter(i -> i <= target).collect(Collectors.toList());
        }

        List<Integer> result = new ArrayList<>(bufferPrimes);
        int stepNumber = 0;
        int start;
        while ((start = stepNumber * step) < target) {
            int finalStart = start;
            stepCandidates.forEach(i -> {
                int number = finalStart + i;
                if (number <= target && primeChecker.isPrime(number, result)) {
                    result.add(number);
                }
            });
            stepNumber++;
        }
        return result;
    }
}
