/*
 * Vincent Gruse
 * Programming Assignment 1
 * Problem 3 (Word Puzzle)
 * WordPuzzle takes in user input to define the size of the grid and word lists.
 * Then using this data, outputs each word found in the grid, including its starting and ending locations.
 * The program also creates an output file that reveals the location of each word.
 * 
 * This was my first attempt, looping through words, rows, columns, and directions.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WordPuzzle {
    static int rows, cols, numWords; // Number of words to find

    public static void main(String args[]) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter the number of rows in the grid: ");
            rows = sc.nextInt();
            System.out.print("Enter the number of columns in the grid: ");
            cols = sc.nextInt();
            System.out.print("Enter the number of words to find: ");
            numWords = sc.nextInt();

            sc.close();

            // Read the grid from a file
            char[][] grid = readGridFromFile("puzzleinput.txt");
            // Read words to find from a file
            String[] wordsToFind = readWordsFromFile("wordlist.txt");

            // Convert words to uppercase to match input file
            for (int i = 0; i < wordsToFind.length; i++) {
                wordsToFind[i] = wordsToFind[i].toUpperCase();
            }

            // Search for words in the grid and get a modified grid
            char[][] answerGrid = searchWordsInGrid(grid, wordsToFind);

            // Write the modified grid to the output file
            writeGridToFile(answerGrid, "puzzleoutput.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    // Read the grid from a file and return it as a 2D character array
    public static char[][] readGridFromFile(String fileName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));

        char[][] grid = new char[rows][cols];
        for (int i = 0; i < grid.length; i++) {
            String row = sc.nextLine();
            grid[i] = row.toCharArray();
        }

        sc.close();
        return grid;
    }

    // Read words to find from a file and return them as an array of strings
    public static String[] readWordsFromFile(String fileName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));

        String[] words = new String[numWords];
        for (int i = 0; i < numWords; i++) {
            words[i] = sc.nextLine();
        }

        sc.close();
        return words;
    }

    // Search for words in the grid and return a modified grid with found letters
    public static char[][] searchWordsInGrid(char[][] grid, String[] wordsToFind) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        // Create an answer grid to keep track of found letters
        char[][] answerGrid = new char[numRows][numCols];

        // Initialize answer grid with spaces
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                answerGrid[i][j] = ' ';
            }
        }

        // Eight possible directions (horizontal, vertical, and diagonal)
        int[][] directions = {
                {-1, 0}, {1, 0}, // Vertical (up/down)
                {0, -1}, {0, 1}, // Horizontal (left/right)
                {-1, -1}, {1, 1}, {-1, 1}, {1, -1}  // Diagonal (four Corners)
        };

        //Loop through words, rows, columns, and directions
        for (String word : wordsToFind) {
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    for (int[] dir : directions) {
                        int dirRow = dir[0];
                        int dirCol = dir[1];
        
                        // Check if the current word can be found starting from the current position
                        if (checkWord(grid, word, row, col, dirRow, dirCol)) {
                            // Calculate the ending position of the found word
                            int endRow = row + (word.length() - 1) * dirRow;
                            int endCol = col + (word.length() - 1) * dirCol;
        
                            // Print info about the found word
                            System.out.printf("Found word: %s \tStart: [%s][%s]\tEnd: [%s][%s]\n", word, row, col, endRow, endCol);
        
                            // Mark found letters in the modified grid
                            makeAnswerGrid(answerGrid, word, row, col, dirRow, dirCol);
                        }
                    }
                }
            }
        }
        return answerGrid;
    }

    // Check if a word is present in the grid starting at a specific position and direction
    private static boolean checkWord(char[][] grid, String word, int row, int col, int dirRow, int dirCol) {
        int numRows = grid.length;
        int numCols = grid[0].length;
    
        int currentRow = row;
        int currentCol = col;
    
        int i;
        for (i = 0; i < word.length(); i++) {
            // Check if the current position is outside the grid boundaries or if the character does not match the word
            if (currentRow < 0 || currentRow >= numRows || currentCol < 0 || currentCol >= numCols || grid[currentRow][currentCol] != word.charAt(i)) {
                break;
            }
    
            // Move to the next position
            currentRow += dirRow;
            currentCol += dirCol;
        }
        return i == word.length();
    }

    // Mark found letters in the modified grid
    private static void makeAnswerGrid(char[][] answers, String word, int row, int col, int dirRow, int dirCol) {
        int currentRow = row;
        int currentCol = col;

        for (int i = 0; i < word.length(); i++) {
            answers[currentRow][currentCol] = word.charAt(i);
            currentRow += dirRow;
            currentCol += dirCol;
        }
    }

    // Write the modified grid to a file
    public static void writeGridToFile(char[][] grid, String fileName) throws FileNotFoundException{
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    writer.write(grid[i][j]);
                }
                writer.write('\n'); // New line
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
