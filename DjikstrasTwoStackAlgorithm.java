/*
Name: Alex Yuk
Date: 10/22/19
File: Djikstras Two Stack Algorithms
*/

import edu.princeton.cs.algs4.StdIn;

public class DjikstrasTwoStackAlgorithm {

    // For clarity
    private enum Operation {
        Addition, Subtraction, Multiplication,  Division, LeftParentheses
    }

    public static void main(String[] args) {
        System.out.print("Please enter complete expression\nEx.(1+((2+3)*(4*5)))\nEnter -1 if you want to use the example\n? ");

        String completeExpression = StdIn.readLine();
        if (Integer.valueOf(completeExpression) == -1)
            completeExpression = "(1+((2+3)*(4*5)))";

        char[] completeExpressionArray = completeExpression.toCharArray();

        System.out.println("Completely Parenthetical evaluation: " + completelyParentheticalEvaluation(completeExpressionArray) + "\n");


        System.out.print("Please enter incomplete expression\nEx.1+(2+3*4+5)*6\nEnter -1 if you want to use the example\n? ");
        String incompleteExpression = StdIn.readLine();
        if (Integer.valueOf(incompleteExpression) == -1)
            incompleteExpression = "1+(2+3*4+5)*6";

        char[] incompleteExpressionArray = incompleteExpression.toCharArray();

        System.out.println("Incompletely Parenthetical evaluation: " + incompletelyParentheticalEvaluation(incompleteExpressionArray));
    }

    private static double completelyParentheticalEvaluation(char[] expressionArray) {
        // Creates two stacks, one to store operands, one to store operators
        Stack<Double> operands = new Stack<Double>();
        Stack<Operation> operators = new Stack<Operation>();

        for (int i = 0; i < expressionArray.length; i++) {
            char currentChar = expressionArray[i];
            switch(currentChar) {
                case '(':
                    break;
                case ')':
                    // Pops two operands and one operator when it needs to calculate
                    operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    operators.push(toOperation(currentChar));
                    break;
                // If currentChar is a number
                default:
                    operands.push(Double.parseDouble(Character.toString(currentChar)));
                    break;
            }
        }
        // Returns the final number in the stack
        return operands.peek();
    }

    private static double incompletelyParentheticalEvaluation(char[] expressionArray) {
        // Creates two stacks, one to store operands, one to store operators
        Stack<Double> operands = new Stack<Double>();
        Stack<Operation> operators = new Stack<Operation>();

        for (int i = 0; i < expressionArray.length; i++) {
            char currentChar = expressionArray[i];
            switch(currentChar) {
                case '(':
                    operators.push(Operation.LeftParentheses);
                    break;
                case ')':
                    // Does all calculations in the parentheses before moving on
                    while (operators.peek() != Operation.LeftParentheses)
                        operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
                    // Pops the LeftParentheses
                    operators.pop();
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    Operation op = toOperation(currentChar);
                    // Checks the order of operations
                    while (!operators.isEmpty() && higherOrder(operators.peek(), op))
                        operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
                    // Then adds the current operation back into the stack
                    operators.push(op);
                    break;
                default:
                    operands.push(Double.parseDouble(Character.toString(currentChar)));
                    break;
            }
        }
        while (operands.size() != 1)
            operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
        return operands.peek();
    }

    // Converts Character into Operation
    private static Operation toOperation(Character c) {
        switch (c) {
            case '+':
                return Operation.Addition;
            case '-':
                return Operation.Subtraction;
            case '*':
                return Operation.Multiplication;
            case '/':
                return Operation.Division;
            default:
                return null;
        }
    }

    // Calculates x, y with operation operator
    private static double calculate(double x, double y, Operation operator) {
        switch (operator) {
            case Addition:
                return y + x;
            case Subtraction:
                return y - x;
            case Multiplication:
                return y * x;
            case Division:
                return y / x;
            default:
                return 0;
        }
    }

    // Check higher order and returns true if operatorX is higher than operatorY
    private static boolean higherOrder(Operation operatorX, Operation operatorY) {
        return ((operatorX == Operation.Multiplication || operatorX == Operation.Division)
                && (operatorY == Operation.Addition || operatorY == Operation.Subtraction));
    }
}

