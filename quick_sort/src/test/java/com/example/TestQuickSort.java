package com.example;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;


public class TestQuickSort {

    // Results
    // Comparing was made 26 times for Integer
    // Comparing was made 20 times for Integer Implemented
    // Comparing was made 23 times for String
    // Comparing was made 22 times for String Implemented

    public class Counter<T> implements Comparator<T> {
        // class to count comparing amount
        private final AtomicInteger counter;
        private final Comparator<T> comparator;
    
        public Counter(Comparator<T> comparator) {
            this.counter = new AtomicInteger();
            this.comparator = comparator;
        }
    
        @Override
        public int compare(final T o1, final T o2) {
            counter.incrementAndGet();
            return comparator.compare(o1, o2);
        }
    
        public int getTotalCalled() {
            return this.counter.get();
        }
    }

    @Test
    @DisplayName("Test for String type")
    public void testStringSort() {

        String[] a_str = {"abc", "abcd", "a", "ab", "abc", "abcd", "abc", "abcd", "abc", "abcd"};
        String[] a_str_save = {"abc", "abcd", "a", "ab", "abc", "abcd", "abc", "abcd", "abc", "abcd"};
        String[] res = {"a", "ab", "abc", "abc", "abc", "abc", "abcd", "abcd", "abcd", "abcd"};

        Counter<String> counter = new Counter<String>(new Comparator<String>() {
            @Override
            public int compare(String p1, String p2) {
                return p1.compareTo(p2);   
            
            }
        });

        QuickSort.sort(a_str, counter);
        System.out.printf("Comparing was made %d times for String\n", counter.getTotalCalled());

        for (int i = 0; i < a_str.length; i++){
            assertEquals(a_str[i], res[i], "Found wrong element");
        }

        Counter<String> counter2 = new Counter<String>(new Comparator<String>() {
            @Override
            public int compare(String p1, String p2) {
                return p1.compareTo(p2);   
            
            }
        });

        Arrays.sort(a_str_save, counter2);
        System.out.printf("Comparing was made %d times for String Implemented\n", counter2.getTotalCalled());

        String[] a_str2 = {"abc", "abcd", "a", "ab", "abc", "abcd", "abc", "abcd", "abc", "abcd"};
        QuickSort.sort(a_str2);
        for (int i = 0; i < a_str2.length; i++){
            assertEquals(a_str2[i], res[i], "Found wrong element");
        }
    }

    @Test
    @DisplayName("Test for Integer type")
    public void testIntSort() {

        Integer[] a = {4, 3, 2, 1, 8, 9, 4, 4, 4, 10};
        Integer[] a_save = {4, 3, 2, 1, 8, 9, 4, 4, 4, 10};
        Integer[] res = {1, 2, 3, 4, 4, 4, 4, 8, 9, 10};

        Counter<Integer> counter = new Counter<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer p1, Integer p2) {
                return p1.intValue() - p2.intValue();
            }
        });

        QuickSort.sort(a, counter);
        System.out.printf("Comparing was made %d times for Integer\n", counter.getTotalCalled());
        
        for (int i = 0; i < a.length; i++){
            assertEquals(a[i], res[i], "Found wrong element");
        }

        Counter<Integer> counter2 = new Counter<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer p1, Integer p2) {
                return p1.intValue() - p2.intValue();
            }
        });
        
        Arrays.sort(a_save, counter2);
        System.out.printf("Comparing was made %d times for Integer Implemented\n", counter2.getTotalCalled());

        Integer[] a2 = {4, 3, 2, 1, 8, 9, 4, 4, 4, 10};
        QuickSort.sort(a2);
        for (int i = 0; i < a2.length; i++){
            assertEquals(a2[i], res[i], "Found wrong element");
        }
    }
}
