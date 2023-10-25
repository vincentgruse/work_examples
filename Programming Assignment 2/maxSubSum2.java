/**
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