package my.test.impl;

import my.test.PrimesFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CachingPrimesFinder implements PrimesFinder {
    private final PrimesFinder wrapped;
    private final List<Integer> cache = new ArrayList<>();

    public CachingPrimesFinder(PrimesFinder wrapped) {
        this.wrapped = wrapped;
        cache.add(2);
    }

    @Override
    public List<Integer> findPrimesLessThan(int target) {
        Integer last = cache.get(cache.size() - 1);
        if (last >= target) {
            return cache.stream().filter(i -> i <= target).collect(Collectors.toList());
        }

        List<Integer> newValue = wrapped.findPrimesLessThan(target);
        newValue.stream().skip(cache.size()).forEach(cache::add);

        return new ArrayList<>(cache);
    }
}
