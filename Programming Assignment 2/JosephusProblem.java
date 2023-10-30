/*
 * Vincent Gruse
 * Programming Assignment 2
 * Problem 2 (Josephus Problem)
 * The program simulates the Josephus problem by taking in a number of soldiers and a value, n, 
 * and repeatedly removes or "kills" the n-th name from the list and displays it.  
 * Finally, the survivor is returned.  
 */

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class JosephusProblem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int soldierNum = 0;
        int positionVal = 0;

        boolean validInput = false;

        // Loop until a valid input for the number of soldiers is entered
        while (!validInput) {
            System.out.print("How many soldiers?: ");
            if (sc.hasNextInt()) {
                soldierNum = sc.nextInt();
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter an integer value.");
                sc.next();
            }
        }

        // Reset the validInput variable for the next input
        validInput = !validInput;
        sc.nextLine();

        // Create a circular array for the soldiers
        Queue<String> circArray = new LinkedList<>();
        
        System.out.println("Please enter " + soldierNum + " soldier names:");

        // Iterate over the number of soldiers and add their names to the queue
        for (int i = 0; i < soldierNum; i++) {
            circArray.add(sc.nextLine() + "(" + (i + 1) + ")");
        }

        // Loop until a valid input for the position is entered
        while (!validInput) {
            System.out.print("\nEnter the position: ");
            if (sc.hasNextInt()) {
                positionVal = sc.nextInt();
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter an integer value.");
                sc.next();
            }
        }
        sc.close();

        killSoldiers(circArray, positionVal);
    }

    public static void killSoldiers(Queue<String> soldiers, int position) {
        long startTime = System.currentTimeMillis();
        System.out.println("\nEliminating order:");
        int count = 1;

        // Eliminate soldiers until only one is left in the queue
        while (soldiers.size() > 1) {
            // Iterate through the queue
            for (int i = 0; i < position - 1; i++) {
                soldiers.add(soldiers.poll());
            }

            // Eliminate the soldier at the current position
            String killed = soldiers.poll();
            System.out.println(count + ". " + killed);
            count++;
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        // Print the survivor
        String survivor = soldiers.poll();
        System.out.println("\nThe survivor is " + survivor + ".");
        System.out.println("Runtime: " + elapsedTime + "ms.");
    }
}
