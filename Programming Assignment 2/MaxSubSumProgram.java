import java.util.Scanner;
import java.util.Random;

public class MaxSubSumProgram {
    /*
    * Algorithm 1
    * Cubic maximum contiguous subsequence sum algorithm
    */
    public static int maxSubSum1(int[] a) {
        int maxSum = 0;

        for(int i = 0; i < a.length; i++) {
            for(int j = 1; j < a.length; j++) {
                int thisSum = 0;

                for (int k = i; k <= j; k++) {
                    thisSum += a[k];
                }
                if (thisSum > maxSum) {
                    maxSum = thisSum;
                }
            }
        }
        return maxSum;
    }

    /*
    * Algorithm 2
    * Quadratic maximum contiguous subsequence sum algorithm
    */
    public static int maxSubSum2(int[] a) {
        int maxSum = 0;

        for(int i = 0; i < a.length; i++) {
            int thisSum = 0;
            for(int j = 1; j < a.length; j++) {
                thisSum += a[j];

                if(thisSum > maxSum) {
                    maxSum = thisSum;
                }
            }
        }
        return maxSum;
    }

    /*
    * Recursive maximum contiguous subsequence sum algorithm.
    * Finds maximum sum in subarray spanning a[left..right].
    * Does not attempt to maintain actual best sequence.
    */
    private static int maxSumRec(int[] a, int left, int right) {
        if (left == right) {
            if ( a[left] > 0) {
                return a[left];
            } else {
                return 0;
            }
        }
        int center = (left + right) / 2;
        int maxLeftSum = maxSumRec(a, left, center);
        int maxRightSum = maxSumRec(a, center + 1, right);
    
        int maxLeftBorderSum = 0, leftBorderSum = 0;
        for (int i = center; i >= left; i--) {
            leftBorderSum += a[i];
            if (leftBorderSum > maxLeftBorderSum) {
                maxLeftBorderSum = leftBorderSum;
            }
        }
    
        int maxRightBorderSum = 0, rightBorderSum = 0;
        for (int i = center + 1; i <= right; i++) {
            rightBorderSum += a[i];
            if (rightBorderSum > maxRightBorderSum) {
                maxRightBorderSum = rightBorderSum;
            }
        }
        return max3(maxLeftSum, maxRightSum, maxLeftBorderSum + maxRightBorderSum);
    }

    private static int max3(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }
    
    /*
    * Driver for divide-and-conquer maximum contiguous
    * subsequence sum algorithm.
    */
    public static int maxSubSum3(int[] a) {
        return maxSumRec(a, 0, a.length - 1);
    }

    /*
    * Linear-time maximum contiguous subsequence sum algorithm.
    */
    public static int maxSubSum4(int[] a) {
        int maxSum = 0, thisSum = 0;

        for(int j = 0; j < a.length; j++) {
            thisSum += a[j];

            if(thisSum > maxSum) {
                maxSum = thisSum;
            } else if (thisSum < 0) {
                thisSum = 0;
            }
        }
        return maxSum;
    }

    public static int[] generateRandomArr(int n) {
        int[] a = new int[n];
        Random rand = new Random();
        int min = -5000;
        int max = 5000;
        
        for (int i = 0; i < n; i++) {
            // Generate a random number between -5000 and 5000 (inclusive)
            int randomNumber = rand.nextInt((max - min) + 1) + min;
            a[i] = randomNumber;
        }
        
        return a;
    }

    private static void measureRuntime(int[] arrSizes, int algorithmChoice) {

        int[][] arrList = new int[5][];

        for (int i = 0; i < arrSizes.length; i++) {
            long startTime = System.currentTimeMillis();
            arrList[i] = generateRandomArr(arrSizes[i]);
            long endTime = System.currentTimeMillis();
            System.out.println("RandomArray[" + arrSizes[i] + "] generated in " + (endTime - startTime) + "ms.");
        }

        System.out.println();
        String[] options = {
                "Algorithm 1: Cubic maximum contiguous subsequence sum algorithm.",
                "Algorithm 2: Quadratic maximum contiguous subsequence sum algorithm.",
                "Algorithm 3: Divide-and-conquer maximum contiguous subsequence sum algorithm.",
                "Algorithm 4: Linear-time maximum contiguous subsequence sum algorithm."
        };

        System.out.println("Running " + options[algorithmChoice - 1]);
        System.out.println("Runtimes:");

        for (int[] arr : arrList) {
            long startTime = System.currentTimeMillis();
            switch (algorithmChoice) {
                case 1:
                    maxSubSum1(arr);
                    break;
                case 2:
                    maxSubSum2(arr);
                    break;
                case 3:
                    maxSubSum3(arr);
                    break;
                case 4:
                    maxSubSum4(arr);
                    break;
            }
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            long minutes = (elapsedTime / 60000);
            long seconds = (elapsedTime / 1000) % 60;
            long milliseconds = elapsedTime % 1000;

            System.out.println("\tN = " + arr.length + ": " + minutes + " mins, " + seconds + " s, " + milliseconds + " ms.");
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Program options:");
            System.out.println("\tAlgorithm 1: Cubic maximum contiguous subsequence sum algorithm.");
            System.out.println("\tAlgorithm 2: Quadratic maximum contiguous subsequence sum algorithm.");
            System.out.println("\tAlgorithm 3: Divide-and-conquer maximum contiguous subsequence sum algorithm.");
            System.out.println("\tAlgorithm 4: Linear-time maximum contiguous subsequence sum algorithm.");
            System.out.println();
            System.out.print("Choose your program: ");

            boolean validInput = false;
            int userChoice = 0;

            // Loop until a valid input is entered
            while (!validInput) {
                if (sc.hasNextInt()) {
                    userChoice = sc.nextInt();
                    if (userChoice > 0 && userChoice < 5) {
                        System.out.println();
                        validInput = true;
                    }
                } else {
                    System.out.println("Invalid input. Please select a valid algorithm.");
                    sc.next();
                }
            }

            int[] sizes = {5000, 10000, 50000, 100000, 200000};

            measureRuntime(sizes, userChoice);
         }
    }
}
