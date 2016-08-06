/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
		if (list.size() <= 1) {
			return list;
		}
		List<T> half1 = mergeSort(new LinkedList<T>(list.subList(0, list.size()/2)), comparator);
		List<T> half2 = mergeSort(new LinkedList<T>(list.subList(list.size()/2, list.size())), comparator);
		insertionSort(half1, comparator);
		insertionSort(half2, comparator);
		List<T> merged = new LinkedList<T>();
		for (int i = 0; i < list.size(); i++) {
			if (half1.size() == 0) {
				merged.add(half2.remove(0));
			} else if (half2.size() == 0) {
				merged.add(half1.remove(0));
			 } else if (comparator.compare(half1.get(0), half2.get(0)) < 0) {
			 	merged.add(half1.remove(0));
			 } else {
				 merged.add(half2.remove(0));
			 }
		}
		return merged;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
		for (T element : list) {
			heap.add(element);
		}
		list.clear();
		while (!heap.isEmpty()) {
			T removed = heap.poll();
			list.add(removed);
		}
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
		for (T element : list) {
			if (heap.size() < k) {
				heap.offer(element);
		 	} else {
		 		T head = heap.peek();
		 		if (comparator.compare(head, element) < 0) {
		 			heap.poll();
		 	        heap.offer(element);
		 		}
		 	}
		 }
		List<T> klist = new ArrayList<T>();
			while (!heap.isEmpty()) {
				T removed = heap.poll();
				klist.add(removed);
			}
		return klist;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
