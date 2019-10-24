/*
Name: Alex Yuk
File: Josephus Problem
Date: 10/22/19
 */

import edu.princeton.cs.algs4.StdIn;

public class JosephusProblem {

    public static void main(String[] args) {
        System.out.print("Please enter number of players\n? ");
        int N = StdIn.readInt();

        System.out.print("Please enter number of players to be skipped\n? ");
        int S = StdIn.readInt();

        System.out.println("You will survive if you sit at position " + solveForSurvival(N,S));
        System.out.println("You will survive if you sit at position (Recursion) " + solveForSurvivalRecursive(N,S));
    }

    public static int solveForSurvival(int N, int S) {
        Queue<Integer> queue = new Queue<>();

        for (int i = 0; i < N; i++) {
            queue.enqueue(i);
        }

        int final_position = 0;

        while(!queue.isEmpty()) {
            for (int i = 1; i <= S; i++) {
                int temp = queue.dequeue();
                if (i == S) {
                    System.out.print(temp + " ");
                    final_position = temp;
                    break;
                }
                queue.enqueue(temp);
            }
        }
        System.out.println("");

        return final_position;
    }

    // Recursion for Josephus problem
    private static int solveForSurvivalRecursive(int N, int S) {
        // Base case: When one person left, they survive
        if (N == 1)
            return 1;
        // Step case: Survivor from n-1 and add onto people skipped and mod for index of current survivor
        else
            return (solveForSurvivalRecursive(N - 1, S) + S - 1) % N + 1;
    }

}
