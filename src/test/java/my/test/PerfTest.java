package my.test;

import my.test.impl.CachingPrimesFinder;
import my.test.impl.SimplePrimesFinder;
import my.test.impl.StepPrimesFinder;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 3)
public class PerfTest {

    @State(Scope.Thread)
    public static class PerfState {
        public PrimesFinder simplePrimesFinder = new SimplePrimesFinder();
        public PrimesFinder stepPrimesFinder = new StepPrimesFinder(17);

        public PrimesFinder cachingPrimesFinder = new CachingPrimesFinder(stepPrimesFinder);
        public Random random = new Random();
        public List<Integer> input = IntStream.generate(() -> random.nextInt(2, 10_000))
                .limit(10).boxed().collect(Collectors.toList());
    }


    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 2)
    public void testSimple(PerfState state) {
        state.input.forEach(i -> state.simplePrimesFinder.findPrimesLessThan(i));
    }

    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 2)

    public void testStep(PerfState state) {
        state.input.forEach(i -> state.stepPrimesFinder.findPrimesLessThan(i));
    }

    @Benchmark
    @Fork(value = 1)
    @Warmup(iterations = 2)
    public void testCaching(PerfState state) {
        state.input.forEach(i -> state.cachingPrimesFinder.findPrimesLessThan(i));
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
}
