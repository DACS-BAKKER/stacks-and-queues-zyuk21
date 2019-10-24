/*
Name: Alex Yuk
Date: 10/22/2019
File: Specialized Node for Eight Queens Memoization
 */

public class QueenNode {
    public Location element;
    public QueenNode next;
    public LinkedList<Location> wrongList = new LinkedList<Location>();
}

