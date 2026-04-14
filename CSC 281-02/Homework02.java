public class Homework02 
{
    static long start, end;
    static double fibElapsed, iterElapsed;
    public static void main(String[] args) {
        int[] fibArray = {5, 10, 15, 20, 25, 30, 35, 40, 100};
        printTable(fibArray);
    }

    public static void printTable(int[] _fibArray) {
        System.out.println("|Fibonacci Number (n)|");
        for (int i : _fibArray) {
            if (i == 100){
                System.out.printf("%d|%s|%s|%d|%.1f|%n", i, "Too Large", "Too Large", fibIterative(i), iterElapsed);
                continue;
            } else {
            System.out.printf("%d|%d|%.1f|%d|%.1f|%n", i,fibRecursiveTrack(i), fibElapsed, fibIterative(i), iterElapsed);
            }
            System.out.println("----------------");
            
        }
        System.out.println("===========");
    }

    public static int fibRecursiveTrack(int n){
        start = System.nanoTime();
        int fibResult = fibRecursive(n);
        end = System.nanoTime() - start;
        fibElapsed = end / 1_000_000.0;
        return fibResult;
    }
    
    public static int fibRecursive(int n) {
        if (n == 0){
            return 0;
        }
        if (n == 1){
            return 1;
        }
        return fibRecursive(n-1) + fibRecursive(n-2);
    }

    public static int fibIterative(int n){
        start = System.nanoTime();
        int[] iterArray = new int[n+1];
        for (int i = 0; i <= n; i++){
            if (i == 0){
                iterArray[i] = 0;
            } else if (i == 1){
                iterArray[i] = 1;
            } else {
                iterArray[i] = iterArray[i-1] + iterArray[i-2];
            }
        }
        end = System.nanoTime() - start;
        iterElapsed = end / 1_000_000;
        return iterArray[n];
    }
}
