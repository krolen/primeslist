package my.test;

import java.math.BigInteger;
import java.util.List;

public class PrimeChecker {

    private final int certainty;

    public PrimeChecker(int certainty) {
        this.certainty = certainty;
    }

    public boolean isPrime(int number, List<Integer> allPrimesUpToNumber) {
        BigInteger target = BigInteger.valueOf(number);
        if (target.isProbablePrime(certainty)) {
            int root = target.sqrt().intValue();
            for (int i = 0; i < allPrimesUpToNumber.size() && allPrimesUpToNumber.get(i) <= root; i++) {
                if (number % allPrimesUpToNumber.get(i) == 0) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

}
