/*
 * Vincent Gruse
 * Programming Assignment 1
 * Problem 3 (Word Puzzle)
 * WordPuzzle takes in user input to define the size of the grid and word lists.
 * Then using this data, outputs each word found in the grid, including its starting and ending locations.
 * The program also creates an output file that reveals the location of each word.
 * 
 * This was my second attempt trying to utilize a trie to search for words.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// TrieNode class for the Trie data structure
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEnd = false;
}

// Trie class for word storage and searching
class Trie {
    TrieNode root = new TrieNode();

    // Insert a word into the Trie
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.isEnd = true;
    }

    // Search for a word in the Trie
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
        }
        return node.isEnd;
    }
}

public class TrieWordPuzzle {
    static int rows, cols, numWords;

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
            char[][] grid = readGridFromFile("puzzleinput.txt"); // Read the grid from a file
            String[] searchWords = readWordsFromFile("wordlist.txt"); // Read words to find from a file

            // Convert words to uppercase to match input file
            for (int i = 0; i < searchWords.length; i++) {
                searchWords[i] = searchWords[i].toUpperCase();
            }

            // Build a Trie from the words
            Trie trie = buildTrie(searchWords);

            // Search for words in the grid and get a modified grid
            char[][] answerGrid = searchWordsInGrid(grid, trie);

            // Write the modified grid to the output file
            writeGridToFile(answerGrid, "puzzleoutput.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    // Build a Trie from an array of words
    public static Trie buildTrie(String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }
        return trie;
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
    public static char[][] searchWordsInGrid(char[][] grid, Trie trie) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        // Create a modified grid to keep track of found letters
        char[][] answerGrid = new char[numRows][numCols];

        // Initialize modifiedGrid with spaces
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

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                for (int[] dir : directions) {
                    int dirRow = dir[0];
                    int dirCol = dir[1];

                    // Search for words starting at this position and direction
                    searchFromPosition(grid, trie, answerGrid, row, col, dirRow, dirCol);
                }
            }
        }

        return answerGrid;
    }

    // Search for words in the grid starting at a specific position and direction
    private static void searchFromPosition(char[][] grid, Trie trie, char[][] answerGrid, int row, int col, int dirRow, int dirCol) {
        int numRows = grid.length;
        int numCols = grid[0].length;

        StringBuilder wordBuilder = new StringBuilder();
        int currentRow = row;
        int currentCol = col;

        while (currentRow >= 0 && currentRow < numRows && currentCol >= 0 && currentCol < numCols) {
            wordBuilder.append(grid[currentRow][currentCol]);
            String currentWord = wordBuilder.toString();

            if (trie.search(currentWord)) {
                int endRow = row + (currentWord.length() - 1) * dirRow;
                int endCol = col + (currentWord.length() - 1) * dirCol;
                System.out.printf("Found word: %s \tStart: [%s][%s]\tEnd: [%s][%s]\n", currentWord, row, col, endRow, endCol);
                // Found a word, mark it in the answerGrid
                markWordInGrid(answerGrid, currentWord, row, col, currentRow, currentCol, dirRow, dirCol);
            }

            currentRow += dirRow;
            currentCol += dirCol;
        }
    }

    // Mark found letters in the modified grid
    private static void markWordInGrid(char[][] answers, String word, int startRow, int startCol, int endRow, int endCol, int dirRow, int dirCol) {
        int currentRow = startRow;
        int currentCol = startCol;

        for (int i = 0; i < word.length(); i++) {
            answers[currentRow][currentCol] = word.charAt(i);
            currentRow += dirRow;
            currentCol += dirCol;
        }
    }

    // Write the modified grid to a file
    public static void writeGridToFile(char[][] grid, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < grid.length; i++) {  // Use grid.length for rows
                for (int j = 0; j < grid[0].length; j++) {  // Use grid[0].length for columns
                    writer.write(grid[i][j]);
                }
                writer.write('\n'); // Add a newline character to separate rows
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
