package algorithms;

import metrics.PerformanceTracker;

public class SelectionSort {
    public static void sort(int[] arr, PerformanceTracker tracker) {
        if (arr == null) throw new IllegalArgumentException("Input array must not be null");
        if (tracker == null) tracker = new PerformanceTracker();

        int n = arr.length;
        tracker.addArrayAccess(1);

        if (isSorted(arr, tracker)) {
            return;
        }

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            tracker.addArrayAccess(1);


            for (int j = i + 1; j < n; j++) {
                tracker.incrementComparisons();
                tracker.addArrayAccess(2);
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                swap(arr, i, minIndex, tracker);
            }
        }
    }


    private static boolean isSorted(int[] arr, PerformanceTracker tracker) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            tracker.incrementComparisons();
            tracker.addArrayAccess(2);
            if (arr[i] > arr[i + 1]) return false;
        }
        return true;
    }


    private static void swap(int[] arr, int i, int j, PerformanceTracker tracker) {
        tracker.addArrayAccess(3);
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        tracker.incrementSwaps();
    }
}