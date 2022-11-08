package my.test.impl;

import my.test.PrimeChecker;
import my.test.PrimesFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple finder which goes over all numbers less than target trying to see if that number is prime
 */
public class SimplePrimesFinder implements PrimesFinder {

    private final PrimeChecker primeChecker;

    public SimplePrimesFinder() {
        primeChecker = new PrimeChecker(5);
    }

    public List<Integer> findPrimesLessThan(int target) {
        if(target < 1) throw new IllegalArgumentException("Argument should be more than 1");

        List<Integer> foundPrimes = new ArrayList<>();

        int num = 2;
        while (num <= target) {
            if(primeChecker.isPrime(num, foundPrimes)) {
                foundPrimes.add(num);
            }
            num++;
        }

        return foundPrimes;
    }
}
