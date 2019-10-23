/*
Name: Alex Yuk
Description: Specialized Stacked Created for Memoization Eight Queens
 */

import java.util.ArrayList;
import java.util.Iterator;

public class QueenStack {

    private QueenNode top;
    private int size;

    public QueenStack() {
        this.top = null;
        this.size = 0;
    }

    public void push(Location element) {
        this.size++;

        QueenNode temp = this.top;
        this.top = new QueenNode();
        this.top.element = element;
        this.top.next = temp;
    }

    public QueenNode pop() {
        this.size--;
        QueenNode temp = top;
        if (top == null)
            return null;
        else
            top = top.next;
        return temp;
    }

    public QueenNode peek() {
        return this.top;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.top == null;
    }



}
