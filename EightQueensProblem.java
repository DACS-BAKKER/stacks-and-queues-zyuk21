/*
Name: Alex Yuk
Date: 10/22/2019
File: Eight Queens Problem With Memoization
 */

import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;

public class EightQueensProblem {

    // N*N board
    // Numbers above 17 take a while
    private static int N = 9;

    public static void main(String[] args) {
        System.out.print("Enter Board Size\n? ");
        N = StdIn.readInt();

        solveQueens();
    }

    // Solves the eight queens problem
    private static void solveQueens() {
        // Stack on locations
        QueenStack st = new QueenStack();
        // ArrayList of previously been locations - used for graphics
        ArrayList<Location> loc = new ArrayList<Location>();
        // This node stores the wrong locations of the first placement
        st.push(null);
        // Off by one with the double popping you will see later
        st.push(new Location(0, 0));
        // For graphics in the console
        Character[][] board = new Character[N][N];
        // Keep track of rotations
        int counter = 0;

        while (loc.size() < N) {
            if (findSafe(st, loc) == null) {
                /*
                 Adding the location of the node that is being popped to the wrong location
                 list of the previous node so it won't repeat
                 */
                Location removed = st.pop().element;
                st.peek().wrongList.add(removed);

                /*
                 Pop again because the wrong location will also be added to the wrong location
                 of the node two before the one removed.

                 This is done so that it will eliminate some board combinations even if the computer
                 hasn't done them yet. It makes the program much faster as the board size increases.
                 */
                QueenNode temp = st.pop();
                st.peek().wrongList.add(removed);

                // Adding back in the one that doesn't have to be removed
                st.push(temp.element);
                st.peek().wrongList = temp.wrongList;

                // -1 so it doesn't go out of bounds
                if (loc.size() != 0)
                    loc.remove(loc.size() - 1);

            } else {
                // Adding a clear location to the stack
                st.push(findSafe(st, loc));
                loc.add(st.peek().element);
            }

            counter++;
            System.out.println("Rotation: " + counter);
            printBoard(board, loc,st);
        }

        System.out.println("-Final-");
        printBoard(board, loc, st);
        System.out.println("Total Rotations: " + counter);
    }

    // Finds a location that is clear for a placement
    private static Location findSafe(QueenStack st, ArrayList<Location> loc) {
        for (int y = 0; y < N; y++)
            for (int x = 0; x < N; x++)
                if (isSafe(st, loc, x, y))
                    return new Location(x, y);
        return null;
    }

    // Check to see if the specific location is clear
    private static boolean isSafe(QueenStack st, ArrayList<Location> loc, int x, int y) {
        // This is to see if the location is in the way of any previous queens
        for (int i = 0; i < loc.size(); i++)
            if ((loc.get(i).getX() - x == loc.get(i).getY() - y) || (loc.get(i).getX() + loc.get(i).getY() == x + y)
                    || (loc.get(i).getX() == x) || (loc.get(i).getY() == y))
                return false;

        // Skips location of getWrong list from the node before
        for (int i = 0; i < st.peek().wrongList.size(); i++)
            if (st.peek().wrongList.get(i).getX() == x && st.peek().wrongList.get(i).getY() == y)
                return false;

        // Skips location of getWrong list from the node two before
        QueenNode temp = st.pop();
        for (int i = 0; i < st.peek().wrongList.size(); i++)
            if (st.peek().wrongList.get(i).getX() == x && st.peek().wrongList.get(i).getY() == y) {
                st.push(temp.element);
                st.peek().wrongList = temp.wrongList;
                return false;

            }
        st.push(temp.element);
        st.peek().wrongList = temp.wrongList;

        return true;
    }

    // Prints the graphic for queens of the board in the console
    private static void printBoard(Character[][] board, ArrayList<Location> loc, QueenStack st) {
        // Empty locations are marked with "."
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                board[i][j] = '.';

        for (int i = 0; i < st.peek().wrongList.size(); i++)
            board[st.peek().wrongList.get(i).getX()][st.peek().wrongList.get(i).getY()] = '-';

        QueenNode temp = st.pop();
        for (int i = 0; i < st.peek().wrongList.size(); i++)
            board[st.peek().wrongList.get(i).getX()][st.peek().wrongList.get(i).getY()] = '-';
        st.push(temp.element);
        st.peek().wrongList = temp.wrongList;

        // Unsafe locations are marked with "x"
        for (int i = 0; i < loc.size(); i++)
            for (int x = 0; x < N; x++)
                for (int y = 0; y < N; y++)
                    if (((loc.get(i).getX() - x == loc.get(i).getY() - y)
                            || (loc.get(i).getX() + loc.get(i).getY() == x + y) || (loc.get(i).getX() == x)
                            || (loc.get(i).getY() == y)) && !(loc.get(i).getX() == x && (loc.get(i).getY() == y)))
                        board[x][y] = 'x';

        // Taken locations are marked with "Q"
        for (int i = 0; i < loc.size(); i++)
            board[loc.get(i).getX()][loc.get(i).getY()] = 'Q';

        // Prints out the 2D array row by row
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++)
                System.out.print(board[x][y] + " ");

            System.out.println("");
        }

        System.out.println("");
    }

}
