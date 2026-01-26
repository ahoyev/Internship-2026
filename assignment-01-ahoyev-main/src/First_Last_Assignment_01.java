
public class First_Last_Assignment_01 {

    // Arrays
    static int[] toSum = {10, 100, 1000, 10000, 100000, 1000000};
    static long[] timeOfA = new long[toSum.length];
    static long[] timeOfB = new long[toSum.length];
    static long[] timeOfC = new long[toSum.length];
	//-15 You used arrays for timeOfA/B/C, but the spec asks for a “chain” (linked structure) and refers to nodes. 

    // Algorithm A: Simple loop summation
    public static int algorithmA(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum = sum + i;
        }
        return sum;
    }

    // Algorithm B: Nested loop summation
    public static int algorithmB(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                sum = sum + 1;
            }
        }
        return sum;
    }

    // Algorithm C: Formula
    public static int algorithmC(int n) {
        return n * (n + 1) / 2;
    }

    // Method to measure runtimes
    public static void measureRuntimes() {
        for (int i = 0; i < toSum.length; i++) {
            int n = toSum[i];

            // Measure Algorithm A
            long start = System.nanoTime();
            algorithmA(n);
            long end = System.nanoTime();
            timeOfA[i] = end - start;

            // Measure Algorithm B
            start = System.nanoTime();
            algorithmB(n);
            end = System.nanoTime();
            timeOfB[i] = end - start;

            // Measure Algorithm C
            start = System.nanoTime();
            algorithmC(n);
            end = System.nanoTime();
            timeOfC[i] = end - start;
        }
    }

    // Display results
    public static void displayResults() {
        System.out.printf("%-10s %-15s %-15s %-15s%n", "N", "Time A (ns)", "Time B (ns)", "Time C (ns)");
        for (int i = 0; i < toSum.length; i++) {
            System.out.printf("%-10d %-15d %-15d %-15d%n", 
                    toSum[i], timeOfA[i], timeOfB[i], timeOfC[i]);
        }
    }

	public static void main(String[] args) {
		System.out.println("CS2050 Assignment-01: Tony Hoyev");
		measureRuntimes();
		displayResults();
	}

}
