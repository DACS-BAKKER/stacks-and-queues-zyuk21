/*
Name: Alex Yuk
File: Josephus Problem
Date: 10/16/19
 */

import edu.princeton.cs.algs4.StdIn;

public class JosephusProblem {

    public static void main(String[] args) {
        System.out.print("Please enter number of players\n? ");
        int N = StdIn.readInt();

        System.out.print("Please enter number of players to be skipped\n? ");
        int S = StdIn.readInt();

        System.out.println("You will survive if you sit at position " + solveForSurvival(N,S));
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



}
