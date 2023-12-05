/*
 * Vincent Gruse
 * Programming Assignment 3
 * Problem 4 (Radix Sorting)
 * Takes in 5000 positive integers generated by a random function ranged between 0 and 50000
 * and outputs the first 100 and last 100 elements in ascending order as well as the execution time for this
 * program.
 */

import java.util.Random;
import java.util.Arrays;

class RadixSort {
    private static Random rand = new Random();

    // Generate an array of random numbers
    private static int[] generateRandomNumbers(int count) {
        int[] randomNumbers = new int[count];
        for (int i = 0; i < count; i++) {
            randomNumbers[i] = rand.nextInt(50001); // Generating random numbers between 0 and 50000
        }
        return randomNumbers;
    }

    public static void countingSort(int[] arr, int exponent) {
        int n = arr.length;
        int[] outputArr = new int[n];
        int[] countArr = new int[10];
 
        // Count occurrences of each digit at particular exponent place
        for (int i = 0; i < n; i++) {
            int index = arr[i] / exponent;
            countArr[index % 10]++;
        }
 
        // Adjust the count array to indicate positions
        for (int i = 1; i < 10; i++) {
            countArr[i] += countArr[i - 1];
        }
 
        // Build the output array based on the digit positions
        int i = n - 1;
        while (i >= 0) {
            int index = arr[i] / exponent;
            outputArr[countArr[index % 10] - 1] = arr[i];
            countArr[index % 10]--;
            i--;
        }
 
        // Copy the sorted output array to the original array
        for (i = 0; i < n; i++) {
            arr[i] = outputArr[i];
        }
    }
 
    public static void radixSort(int[] arr) {
        System.out.println("*** Radix Sort ***");
        long startTime = System.nanoTime();
        
        // Find the maximum number in the array
        int maxNum = Arrays.stream(arr).max().orElse(0);

        // Initialize to LSF
        int exponent = 1;

        // Perform counting sort for each digit place (starting from least significant digit)
        while (maxNum / exponent >= 1) {
            countingSort(arr, exponent);
            exponent *= 10; // Move to next digit place
        }

        long endTime = System.nanoTime();
        double runtime = (endTime - startTime) / 1000000.0;

        printArr(arr, runtime);
    }

    public static void printArr(int[] arr, double rT) {
        System.out.print("First 100 and Last 100 elements:\n{");

        // Print the first 100 elements
        for (int i = 0; i < 100; i++) {
            System.out.print(arr[i] + ", ");
        }

        // Placeholder for omitted elements
        System.out.println("\n\n..... ,\n");
        
        // Print the last 100 elements
        for (int i = arr.length - 100; i < arr.length; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println(arr[arr.length - 1] + "}"); // Print the last element
        System.out.println("Runtime: " + rT + " milliseconds\n");
    }

    public static void main(String[] args) {
        // Sort an array of 5000 randomly generated numbers using radix sort
        radixSort(generateRandomNumbers(5000));
    }
}