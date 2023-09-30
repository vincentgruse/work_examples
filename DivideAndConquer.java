/*
 * Vincent Gruse
 * Programming Assignment 1
 * Problem 4 (Majority Element in Array)
 * Method 1 - O(nlogn) - Divide-and-Conquer
 * Takes in user input of text file name containing an array of integers of undefined size and value.
 * Uses binary search method to find the majority element in the array.
 * Outputs the number in the array that is the majority element (occurs the most and greater than half)
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DivideAndConquer {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter the file you would like to check: ");
            String filename = sc.nextLine();

            sc.close();

            // Read integers from the file into an ArrayList
            ArrayList<Integer> intList = readIntFromFile(filename);

            // Find the majority element
            int majorityElement = findMajEle(intList, 0, intList.size() - 1);

            // Print the result
            if (majorityElement != -1) {
                System.out.println("Majority Element: " + majorityElement);
            } else {
                System.out.println("No Majority Element");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    // Reads integers from file into ArrayList
    public static ArrayList<Integer> readIntFromFile(String fileName) throws FileNotFoundException {
        ArrayList<Integer> intList = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    int num = sc.nextInt();
                    intList.add(num);
                } else {
                    // Skip non-integers
                    sc.next();
                }
            }
        }
        return intList;
    }

    // Find the majority element using divide-and-conquer
    public static int findMajEle(ArrayList<Integer> arr, int left, int right) {
        int n = right - left + 1; // Size of the subarray
        if (n == 1) {
            return arr.get(left); // Return the single element in the subarray
        }
        int k = n / 2;
    
        // Recursively find majority elements in left and right subarrays
        int lMajority = findMajEle(arr, left, left + k - 1);
        int rMajority = findMajEle(arr, left + k, right);
    
        // Returns matching majority elements
        if (lMajority == rMajority) {
            return lMajority;
        }
    
        // Count the occurrences of the majority elements
        int lCount = getFreq(arr, lMajority, left, right);
        int rCount = getFreq(arr, rMajority, left, right);
    
        // Determine which is the overall majority
        if (lCount > k) {
            return lMajority;
        } else if (rCount > k) {
            return rMajority;
        } else {
            return -1; // No majority element
        }
    }

    // Count the occurrences of an element
    public static int getFreq(ArrayList<Integer> arr, int element, int left, int right) {
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (arr.get(i) == element) {
                count++;
            }
        }
        return count;
    }
}
