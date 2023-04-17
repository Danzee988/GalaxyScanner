package Benchmarking;

import Methods.HashMaps;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;



public class BenchMarks {
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 1)
    @Fork(1)
    @State(Scope.Thread)

    public static class MyState {
        int[] pixelArray = {0, 1, 1, 0, -1, -1, 2, 2, -1, 3, 3, -1};
    }

    @Benchmark
    public void testCreateStarMap(MyState state) {
        HashMaps.createStarMap(state.pixelArray);
    }
}


