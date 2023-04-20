package Benchmarking;

import Methods.Sorting;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SortingBenchmark {

    @Benchmark
    public void testQuickSort() {
        int[] roots = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] sizes = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        Sorting.quickSort(roots, sizes, 0, sizes.length - 1);
    }
}



