package com.example;
import java.util.Comparator;
// import java.util.Random;


public class QuickSort {
    public static <T extends Comparable<T>> void sort(T[] array) {
        quick_sort(array, 0, array.length - 1);
    }

    public static <T extends Comparable<T>> void quick_sort(T[] array, int lo, int hi) {
        if (lo >= hi) {
			return;
		}
		int[] part = break_partition(array, lo, hi);
        if ((part[0] - lo) >= (hi - part[1])) {

        }
		quick_sort(array, lo, part[0]);
		quick_sort(array, part[1], hi);
    }

    public static <T extends Comparable<T>> int[] break_partition(T[] array, int lo, int hi) {
        int i = lo + 1;
		int p = lo;
		int j = hi;
		int g = hi + 1;
		T support_element = array[lo];

		while (true) {
		    while (i < hi && array[i].compareTo(support_element) < 0) {
				i++;
			}
			while(j > lo && array[j].compareTo(support_element) > 0) {
				j--;
			}
			if (i >= j) {
				if (i == j && array[i].compareTo(support_element) == 0) {
					swap(array, i, ++p);
				}
				break;
			}
			swap(array, i, j);
			if (array[i].compareTo(support_element) == 0) {
				swap(array, i, ++p);
			}
			if (array[j].compareTo(support_element) == 0) {
				swap(array, j, --g);
			}
			i++;
			j--;
		}
		for (int k = lo; k <= p; k++) {
			swap(array, k, j--);
		}
		for (int k = hi; k >= g; k--) {
			swap(array, k, i++);
		}
		return new int[] { j, i };
    }

    public static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
    
    public static <T> void sort(T[] array, Comparator<T> comparator) {
        quick_sort2(array,comparator, 0, array.length - 1);
    }

    public static <T> void quick_sort2(T[] array, Comparator<T> comparator, int lo, int hi) {
        if (lo >= hi) {
			return;
		}
		int[] part = break_partition2(array, comparator, lo, hi);
        if ((part[0] - lo) >= (hi - part[1])) {

        }
		quick_sort2(array, comparator, lo, part[0]);
		quick_sort2(array, comparator, part[1], hi);
    }

    public static <T> int[] break_partition2(T[] array, Comparator<T> comparator, int lo, int hi) {
        int i = lo + 1;
		int p = lo;
		int j = hi;
		int g = hi + 1;
		T support_element = array[lo];

		while (true) {
		    while (i < hi && comparator.compare(array[i], support_element) < 0) {
				i++;
			}
			while(j > lo && comparator.compare(array[j], support_element) > 0) {
				j--;
			}
			if (i >= j) {
				if (i == j && comparator.compare(array[i], support_element) == 0) {
					swap(array, i, ++p);
				}
				break;
			}
			swap(array, i, j);
			if (comparator.compare(array[i], support_element) == 0) {
				swap(array, i, ++p);
			}
			if (comparator.compare(array[j], support_element) == 0) {
				swap(array, j, --g);
			}
			i++;
			j--;
		}
		for (int k = lo; k <= p; k++) {
			swap(array, k, j--);
		}
		for (int k = hi; k >= g; k--) {
			swap(array, k, i++);
		}
		return new int[] { j, i };
    }
}

