package cli;


import algorithms.SelectionSort;
import metrics.PerformanceTracker;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {


    public static void main(String[] args) throws Exception {
// defaults
        int[] sizes = new int[]{100, 1000, 10000, 100000};
        int repeats = 3;
        String distribution = "random";

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--sizes") && i + 1 < args.length) {
                sizes = Arrays.stream(args[++i].split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            } else if (args[i].equals("--repeats") && i + 1 < args.length) {
                repeats = Integer.parseInt(args[++i]);
            } else if (args[i].equals("--distribution") && i + 1 < args.length) {
                distribution = args[++i];
            }
        }


        System.out.println("size,trial,distribution,timeNs,comparisons,swaps,arrayAccesses");
        try (PrintWriter pw = new PrintWriter(new FileWriter("benchmark_results.csv"))) {
            pw.println("size,trial,distribution,timeNs,comparisons,swaps,arrayAccesses");


            Random rnd = new Random(42);
            for (int size : sizes) {
                for (int t = 1; t <= repeats; t++) {
                    int[] data = generateArray(size, distribution, rnd);
                    PerformanceTracker tracker = new PerformanceTracker();

                    int[] copy = Arrays.copyOf(data, data.length);

                    long start = System.nanoTime();
                    SelectionSort.sort(copy, tracker);
                    long end = System.nanoTime();
                    long time = end - start;

                    if (!isSorted(copy)) {
                        System.err.println("ERROR: sorted check failed for size=" + size + " distribution=" + distribution);
                    }


                    String line = String.format("%d,%d,%s,%d,%d,%d,%d", size, t, distribution, time, tracker.getComparisons(), tracker.getSwaps(), tracker.getArrayAccesses());
                    System.out.println(line);
                    pw.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to write benchmark_results.csv: " + e.getMessage());
        }
    }

    private static int[] generateArray(int n, String distribution, Random rnd) {
        int[] arr = new int[n];
        switch (distribution) {
            case "sorted":
                for (int i = 0; i < n; i++) arr[i] = i;
                break;
            case "reverse":
                for (int i = 0; i < n; i++) arr[i] = n - i;
                break;
            case "nearly-sorted":
            case "nearly_sorted":
                for (int i = 0; i < n; i++) arr[i] = i;
                int swaps = Math.max(1, n / 100);
                for (int s = 0; s < swaps; s++) {
                    int a = rnd.nextInt(n);
                    int b = rnd.nextInt(n);
                    int tmp = arr[a]; arr[a] = arr[b]; arr[b] = tmp;
                }
                break;
            case "random":
            default:
                for (int i = 0; i < n; i++) arr[i] = rnd.nextInt();
                break;
        }
        return arr;
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) if (arr[i] > arr[i + 1]) return false;
        return true;
    }
}