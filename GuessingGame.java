/*
 * Vincent Gruse
 * Programming Assignment 1
 * Problem 1 (Guessing Game)
 * GuessingGame finds a randomly generated number in a range from 1 to 1000000.
 * It will return the random number, and the number of guessing trials.
 * At the end, the program will determine the average number of guesses it took to find the random number.
 */

import java.util.Random;

public class GuessingGame {
    
    public static int guesses = 1;
    public static int trial = 1;
    public static int[] trialArr = new int[100]; // Stores the number of guesses for each trial

    // Recursive function to find 'num' in range 'left' to 'right'
    int binarySearch (int left, int right, int num) {
        System.out.println("Guess " + guesses + ":");
        if (right >= left) {
            int midpoint = (left + right)/2; // Finds the midpoint of range
            if (midpoint == num) {
                return midpoint;
            }
            guesses++;
            if (midpoint > num) {
                System.out.println("The number is lower than " + midpoint);
                // Maintains left bound, moves right bound to be 1 left of the midpoint
                return binarySearch(left, midpoint - 1, num);
            } else {
                System.out.println("The number is higher than " + midpoint);
                // Maintains right bound, moves left bound to be 1 right of the midpoint
                return binarySearch(midpoint + 1, right, num);
            }
        }
        return -1; // Error not in range
    }

    // Function to average the values in an array--returns the average
    public static double avg(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum/arr.length;
    }

    public static void main(String args[]) {
        GuessingGame search = new GuessingGame();

        while (trial <=100) { // Runs 100 trials
            guesses = 1; // Reset
            int lBound = 1;
            int uBound = 1000000;
            Random random = new Random();
            int randomNum = random.nextInt(uBound) + 1; //1 to 1000000 inclusive
            //System.out.println("The random number is " + randomNum); //Debugging
            int searchResult = search.binarySearch(lBound, uBound, randomNum);

            if (searchResult == -1) {
                System.out.println("The number was not found.");
            } else {
                System.out.println("The number is found.  It is " + searchResult + ".");
                System.out.println("The number of guessing trials was " + guesses + ".");
                trialArr[trial - 1] = guesses; // Saves number of guesses
                trial++; // Increase trial counter
            }
        }
        double average = avg(trialArr);
        System.out.println("The average number of guess trials for 100 random numbers was " + average + ".");
    }
}