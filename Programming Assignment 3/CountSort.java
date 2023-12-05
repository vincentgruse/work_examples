/*
 * Vincent Gruse
 * Programming Assignment 3
 * Problem 3 (Counting Sorting)
 * Takes in 5000 positive integers generated by a random function ranged between 0 and 500
 * and outputs every 100th element (50 elements in total) in the result of this counting sorting program
 * as well as the execution time for this program.
 */

import java.util.Random;
import java.util.Arrays;

class CountSort {
    private static Random rand = new Random();

    // Generate an array of random numbers
    private static int[] generateRandomNumbers(int count) {
        int[] randomNumbers = new int[count];
        for (int i = 0; i < count; i++) {
            randomNumbers[i] = rand.nextInt(501); // Generating random numbers between 0 and 500
        }
        return randomNumbers;
    }

    // Counting sort algorithm
    public static void countingSort(int[] arr) {
        System.out.println("*** Count Sort ***");
        long startTime = System.nanoTime();

        // Finding the maximum element of input array
        int max = Arrays.stream(arr).max().orElse(0);

        // Initializing countArray with 0
        int[] countArr = new int[max + 1];

        // Mapping each element of input array as an index of count array
        for (int num : arr) {
            countArr[num]++; // Counting occurrences of each number
        }

        // Calculating prefix sum at every index
        for (int i = 1; i <= max; i++) {
            countArr[i] += countArr[i - 1]; // Calculating prefix sum
        }

        // Creating output array from count array
        int[] outputArr = new int[arr.length];

        for (int i = arr.length - 1; i >= 0; i--) {
            outputArr[countArr[arr[i]] - 1] = arr[i]; // Placing elements at correct positions
            countArr[arr[i]]--; // Decrementing count after placing an element
        }

        long endTime = System.nanoTime();
        double runtime = (endTime - startTime) / 1000000.0;

        printArr(outputArr, runtime); // Printing the sorted array and runtime
    }

    // Printing every 100th element and runtime
    public static void printArr(int[] arr, double rT) {
        System.out.print("Every 100th element:\n{");
        for (int i = 0; i < arr.length; i += 100) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println(arr[arr.length - 1] + "}"); // Printing the last element
        System.out.println("Runtime: " + rT + " milliseconds\n");
    }

    public static void main(String[] args) {
        countingSort(generateRandomNumbers(5000)); // Sorting 5000 randomly generated numbers
    }
}