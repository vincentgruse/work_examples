/*
 * Vincent Gruse
 * Programming Assignment 1
 * Problem 2 (Decimal to Binary)
 * DecToBin takes a positive decimal number and converts it to a binary number using a recursive function.
 * The quotient is recursively called until returning either '1' or '0', 
 * then the remainder is added from the inner call.
 */

import java.util.Scanner;

public class DecimalToBinaryConverter {
    // Recursive function that takes a positive decimal integer as an argument and converts to binary
    public static String decimalToBinary(int integer) {
        if (integer == 0) {
            return "0";
        } else if (integer == 1) {
            return "1";
        } else {
            // Calculate the remainder when dividing the integer by 2
            int remainder = integer % 2;
            // Calculate the quotient when dividing the integer by 2
            int quotient = integer / 2;
            // Recursively call the convert function and add remainder to the result
            String result = decimalToBinary(quotient);
            // Formatted to match question requirements
            return result + " " + remainder;
        }
    }
    public static void main(String args[]) {
        // Using scanner for user input
        Scanner userNum = new Scanner(System.in);
        int num;

        // Check for valid input
        while (true) {
            System.out.print("Enter a positive decimal number: ");
            if (userNum.hasNextInt()) {
                num = userNum.nextInt();
                if (num >= 0) {
                    break;
                } else {
                    System.out.println("Error: Please enter a positive number.");
                }
            } else {
                // Clear buffer for valid input
                userNum.next();
                System.out.println("Error: Invalid input. Please enter a valid integer.");
            }
        }
        String output = decimalToBinary(num);
        System.out.println("The binary number is: " + output);
        userNum.close(); // Close scanner
    }
}