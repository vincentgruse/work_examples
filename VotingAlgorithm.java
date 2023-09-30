/*
 * Vincent Gruse
 * Programming Assignment 1
 * Problem 4 (Majority Element in Array)
 * Method 2 - O(n) - Moore's Voting Algorithm
 * Takes in user input of text file name containing an array of integers of undefined size and value.
 * Uses voting search method to find the majority element in the array.
 * Outputs the number in the array that is the majority element (occurs the most and greater than half)
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class VotingAlgorithm {
    static int count = 0;
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter the file you would like to check: ");
            String filename = sc.nextLine();

            sc.close();

            // Read integers from the file into an ArrayList
            ArrayList<Integer> intList = readIntFromFile(filename);

            // Find the majority element
            findMajEle(intList);
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
                    // If the next token is not an integer, skip it
                    sc.next();
                }
            }
        }
        return intList;
    }

    // Find the majority element using Moore's Voting Algorithm
    public static void findMajEle(ArrayList<Integer> arr) {
        int majIndex = 0;
        int arrSize = arr.size();

        for (int i = 0; i < arrSize - 1; i++) {
            if (arr.get(majIndex) == arr.get(i)) {
                count++;
            } else {
                count--;
            }
            if (count == 0) {
                majIndex = i;
                count = 1;
            }
        }

        int majValue = arr.get(majIndex);
        int majCount = 0;

        // Count the occurrences of the majority element
        for (int num : arr) {
            if (num == majValue) {
                majCount++;
            }
        }

        // Print the result
        if (majCount > arrSize / 2) {
            System.out.println("Majority Element: " + majValue);
        } else {
            System.out.println("No Majority Element");
        }
    }
}