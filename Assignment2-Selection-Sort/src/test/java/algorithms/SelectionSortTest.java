package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SelectionSortTest {

    @Test
    public void testEmpty() {
        int[] arr = new int[0];
        SelectionSort.sort(arr, new PerformanceTracker());
        assertEquals(0, arr.length);
    }

    @Test
    public void testSingleElement() {
        int[] arr = new int[]{5};
        SelectionSort.sort(arr, new PerformanceTracker());
        assertArrayEquals(new int[]{5}, arr);
    }

    @Test
    public void testAlreadySorted() {
        int[] arr = new int[]{1,2,3,4,5};
        PerformanceTracker pt = new PerformanceTracker();
        SelectionSort.sort(arr, pt);
        assertArrayEquals(new int[]{1,2,3,4,5}, arr);
        assertTrue(pt.getComparisons() >= arr.length - 1);
    }

    @Test
    public void testReverseSorted() {
        int[] arr = new int[]{5,4,3,2,1};
        SelectionSort.sort(arr, new PerformanceTracker());
        assertArrayEquals(new int[]{1,2,3,4,5}, arr);
    }


    @Test
    public void testWithDuplicates() {
        int[] arr = new int[]{3,1,2,3,1};
        SelectionSort.sort(arr, new PerformanceTracker());
        assertArrayEquals(new int[]{1,1,2,3,3}, arr);
    }


    @Test
    public void testRandomLarge() {
        int n = 1000;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = (int)(Math.random()*1000);
        SelectionSort.sort(arr, new PerformanceTracker());
        for (int i = 0; i < n - 1; i++) assertTrue(arr[i] <= arr[i+1]);
    }
}