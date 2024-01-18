package com.example;
import java.util.Comparator;

public class BinarySearch {
    public static <T> Integer find(T[] array, Comparator<T> comparator, T element) {
        int l = 0, r = array.length - 1;

        while (l <= r) {
            int m = l + (r - l) / 2;
 
            // Check if x is present at mid
            if (comparator.compare(array[m], element) == 0)
                return m;
 
            // If x greater, ignore left half
            if (comparator.compare(array[m], element) < 0)
                l = m + 1;
 
            // If x is smaller, ignore right half
            else
                r = m - 1;
        }

        return -1;
    }
}