/*
 * Vincent Gruse
 * Programming Assignment 3
 * Problem 2 (Insert Sorting, Selection Sorting, Bubble Sorting, & Merge Sorting)
 * Takes in 5000 positive integers generated by a random function ranged between 0 and 50000
 * and outputs the first 100 elements in the result of each sorting program and prints the
 * execution time for each program.
 */

import java.util.Random;

class SortingAlgs {
    private static Random rand = new Random();

    // Generate an array of random numbers
    private static int[] generateRandomNumbers(int count) {
        int[] randomNumbers = new int[count];
        for (int i = 0; i < count; i++) {
            randomNumbers[i] = rand.nextInt(50001); // Range: 0 to 50000
        }
        return randomNumbers;
    }

    // Swap function to switch elements in an array
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    // Insertion Sort algorithm
    private static void insertSort(int[] arr) {
        System.out.println("*** Insert Sort ***");
        long startTime = System.nanoTime();

        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];

            // Move elements that are greater than key to one position ahead of their current position
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                swap(arr, j, j + 1);
                j = j - 1;
            }
            arr[j + 1] = key;
        }

        long endTime = System.nanoTime();
        double runtime = (endTime - startTime) / 1000000.0;

        printArr(arr, runtime);
    }

    // Selection Sort algorithm
    private static void selectionSort(int[] arr) {
        System.out.println("*** Selection Sort ***");
        long startTime = System.nanoTime();

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minEle = i;
            // Find the minimum element in unsorted part of the array
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minEle]) {
                    minEle = j;
                }
            }
            // Swap the found minimum element with the first element
            swap(arr, minEle, i);
        }

        long endTime = System.nanoTime();
        double runtime = (endTime - startTime) / 1000000.0;

        printArr(arr, runtime);
    }

    // Bubble Sort algorithm
    private static void bubbleSort(int[] arr) {
        System.out.println("*** Bubble Sort ***");
        long startTime = System.nanoTime();

        int n = arr.length;
        boolean swap;
        for (int i = 0; i < n - 1; i++) {
            swap = false;
            // Traverse the array and swap elements if they are in the wrong order
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swap = true;
                }
            }
            // If no two elements were swapped in inner loop, array is sorted
            if (!swap) {
                break;
            }
        }

        long endTime = System.nanoTime();
        double runtime = (endTime - startTime) / 1000000.0;

        printArr(arr, runtime);
    }

    // Merge function to merge two sorted subarrays
    public static void merge(int[] arr, int[] left, int[] right) {
        int leftLength = left.length;
        int rightLength = right.length;
        int i = 0, j = 0, k = 0;

        // Merge left and right arrays into main array
        while (i < leftLength && j < rightLength) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        // Copy remaining elements of left and right arrays if any
        while (i < leftLength) {
            arr[k++] = left[i++];
        }

        while (j < rightLength) {
            arr[k++] = right[j++];
        }
    }

    // Merge Sort recursion function
    public static void mergeSortRecursion(int[] array) {
        if (array.length <= 1) {
            return;
        }

        int mid = array.length / 2;
        int[] left = new int[mid];
        int[] right = new int[array.length - mid];

        // Split the array into two halves
        System.arraycopy(array, 0, left, 0, mid);
        System.arraycopy(array, mid, right, 0, array.length - mid);

        // Recursively sort the two halves
        mergeSortRecursion(left);
        mergeSortRecursion(right);

        // Merge the sorted halves
        merge(array, left, right);
    }

    // Merge Sort algorithm driver
    private static void mergeSort(int[] arr) {
        System.out.println("*** Merge Sort ***");
        long startTime = System.nanoTime();

        mergeSortRecursion(arr);

        long endTime = System.nanoTime();
        double runtime = (endTime - startTime) / 1000000.0;

        printArr(arr, runtime);

        System.out.println("\nRuntime: " + runtime + " milliseconds");
    }

    // Print the first 100 elements of the array and runtime
    public static void printArr(int[] arr, double rT) {
        System.out.print("First 100 elements:\n{");
        for (int i = 0; i < 100; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println("... , " + arr[arr.length - 1] + "}");
        System.out.println("Runtime: " + rT + " milliseconds\n");
    }

    public static void main(String[] args) {
        int[] tmpArr = generateRandomNumbers(5000);
        int[] insertArr = tmpArr.clone();
        int[] selectionArr = tmpArr.clone();
        int[] bubbleArr = tmpArr.clone();
        int[] mergeArr = tmpArr.clone();

        insertSort(insertArr);
        selectionSort(selectionArr);
        bubbleSort(bubbleArr);
        mergeSort(mergeArr);
    }
}
