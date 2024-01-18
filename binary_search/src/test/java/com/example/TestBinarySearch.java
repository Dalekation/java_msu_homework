package com.example;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

public class TestBinarySearch {
    @Test
    @DisplayName("Test for String type")
    public void testStringSearch() {

        String[] a_str = {"a", "ab", "abc", "abcd"};
        String b_str = "abc";
        Integer res;

        res = BinarySearch.find(a_str, new Comparator<String>() {
            @Override
            public int compare(String p1, String p2) {
                return p1.compareTo(p2);   
            
            }
        }, b_str);

        assertEquals(2, res, "Found wrong element");
    }

    @Test
    @DisplayName("Test for Integer type")
    public void testIntSearch() {

        Integer[] a = {1, 2, 3, 4};
        Integer b = 2;
        Integer res;
        res = BinarySearch.find(a, new Comparator<Integer>() {
            @Override
            public int compare(Integer p1, Integer p2) {
                return p1.intValue() - p2.intValue();
            }
        }, b);

        assertEquals(1, res, "Found wrong element");
    }
}
