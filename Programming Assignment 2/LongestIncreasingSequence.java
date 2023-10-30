/*
 * Vincent Gruse
 * Programming Assignment 2
 * Problem 3 (Longest Sequence)
 * The program has two modes: Test File and Randomly Generated.
 * 
 * Test file mode takes in an array and its size from a .txt file and uses DFS to find the
 * longest increasing sequence of numbers in all 8 directions. The longest sequence and the 
 * list of numbers and locations in the initial 2D matrix are returned.
 * 
 * Randomly Generated mode has the same functionality but takes in user input for the size of 
 * the NxN matrix and randomly generates the matrix using values between 0 and 1000.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LongestIncreasingSequence {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int choice = 0;
        int matrixSize = 0;
        long startTime;

        while (true) {
            System.out.print("Please enter (1) for Test File Version or (2) for Random Function Version: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice == 1 || choice == 2) {
                    break;
                }
            }
        }

        if (choice == 1) {
            System.out.println("***Test File Version***");
            System.out.print("Please enter the size of the given matrix: ");
            matrixSize = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            System.out.print("Enter the file you would like to check: ");
            String filename = sc.nextLine();

            int[][] matrix = readMatrixFromFile(filename, matrixSize);

            startTime = System.currentTimeMillis();

            List<SequenceData> sequence = findLIS(matrix);

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            // Print the sequence
            printSequence(sequence);
            System.out.println("Runtime: " + elapsedTime + " ms.");
        } else {
            System.out.println("***Randomly Generated Version***");
            System.out.print("Please enter the size of the given matrix: ");
            matrixSize = sc.nextInt();
            int[][] matrix = generateMatrix(matrixSize);

            startTime = System.currentTimeMillis();

            List<SequenceData> sequence = findLIS(matrix);

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            // Print the sequence
            printSequence(sequence);
            System.out.println("Runtime: " + elapsedTime + " ms.");
        }

        sc.close();
    }

    // Generate a random matrix
    public static int[][] generateMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Generate a random integer between 0 and 1000
                matrix[i][j] = (int) (Math.random() * 1001);
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        return matrix;
    }

    // Find the longest increasing sequence using DFS
    public static List<SequenceData> findLIS(int[][] matrix) {
        // Get the size of the matrix
        int size = matrix.length;

        // Initialize the longest sequence as an empty list
        List<SequenceData> longestSequence = new ArrayList<>();

        // Iterate over the matrix
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Create a new sequence with the current element
                List<SequenceData> currentSequence = new ArrayList<>();
                currentSequence.add(new SequenceData(matrix[i][j], i, j));

                // Perform DFS to find the longest increasing sequence starting from the current element
                List<SequenceData> sequence = dfs(matrix, i, j, currentSequence);

                // Update the longest sequence if the new sequence found is longer
                if (sequence.size() > longestSequence.size()) {
                    longestSequence = new ArrayList<>(sequence);
                }
            }
        }
        // Return the longest increasing sequence found
        return longestSequence;
    }


    // Recursive DFS to find the longest increasing sequence
    public static List<SequenceData> dfs(int[][] matrix, int x, int y, List<SequenceData> sequence) {
        // Define the movement directions for traversal
        int[] dx = {0, 1, 0, -1, 1, -1, 1, -1};
        int[] dy = {1, 0, -1, 0, 1, -1, -1, 1};

        // Get the value of the last element in the sequence
        int lastNumber = sequence.get(sequence.size() - 1).getValue();
        int size = matrix.length;

        // Initialize the longest sequence with the current sequence
        List<SequenceData> longestSequence = new ArrayList<>(sequence);

        // Iterate over all possible directions
        for (int i = 0; i < 8; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            // Check if the new positions are within the matrix boundaries
            if (newX >= 0 && newX < size && newY >= 0 && newY < size) {
                // Get the value of the new element in the matrix
                int newNumber = matrix[newX][newY];

                // Check if the new element is greater than the last element of the sequence
                // and if the new element is not already present in the sequence
                if (newNumber > lastNumber && !dataCheck(sequence, newNumber, newX, newY)) {
                    // Create a new sequence that includes the new element
                    List<SequenceData> newSequence = new ArrayList<>(sequence);
                    newSequence.add(new SequenceData(newNumber, newX, newY));

                    // Recursively call DFS with the updated sequence
                    List<SequenceData> lis = dfs(matrix, newX, newY, newSequence);

                    // Update the longest sequence if the new sequence found is longer
                    if (lis.size() > longestSequence.size()) {
                        longestSequence = lis;
                    }
                }
            }
        }
        // Return the longest sequence found
        return longestSequence;
    }


    // Check if a SequenceData with a specific value and position is present in the list of pairs
    public static boolean dataCheck(List<SequenceData> sequence, int value, int x, int y) {
        for (SequenceData SequenceData : sequence) {
            if (SequenceData.getValue() == value && SequenceData.getX() == x && SequenceData.getY() == y) {
                return true;
            }
        }
        return false;
    }

    // Print the sequence formatted for readability
    public static void printSequence(List<SequenceData> sequence) {
        System.out.println("Value:\t X:   Y:");
        for (SequenceData SequenceData : sequence) {
            System.out.printf("%4d\t(%2d, %2d)\n", SequenceData.getValue(), SequenceData.getX(), SequenceData.getY());
        }
        System.out.println();
        System.out.println("The length of the sequence is " + sequence.size() + ".");
    }

    // Read a matrix from a file
    public static int[][] readMatrixFromFile(String fileName, int size) {
        int[][] matrix = new int[size][size];
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix[i][j] = sc.nextInt();
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return matrix;
    }
}

// Sequence class to store data about values in the current sequence, could have also used Hashtable
class SequenceData {
    private int value;
    private int x;
    private int y;

    public SequenceData(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
