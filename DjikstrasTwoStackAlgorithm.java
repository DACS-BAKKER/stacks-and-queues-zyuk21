/*
Name: Alex Yuk
File: Djikstras Two Stack Algorithms
Date: 10/16/19
 */

public class DjikstrasTwoStackAlgorithm {

    private enum Operation {
        Addition, Subtraction, Multiplication,  Division, LeftParentheses
    }

    public static void main(String[] args) {
        String completeExpression = "(1+((2+3)*(4*5)))";
        char[] completeExpressionArray = completeExpression.toCharArray();

        System.out.println("Completely Parenthetical evaluation: " + completelyParentheticalEvaluation(completeExpressionArray));

        String incompleteExpression = "1+(2+3*4+5)*6";
        char[] incompleteExpressionArray = incompleteExpression.toCharArray();

        System.out.println("Incompletely Parenthetical evaluation: " + incompletelyParentheticalEvaluation(incompleteExpressionArray));
    }

    private static double completelyParentheticalEvaluation(char[] expressionArray) {
        Stack<Double> operands = new Stack<Double>();
        Stack<Operation> operators = new Stack<Operation>();

        for (int i = 0; i < expressionArray.length; i++) {
            char c = expressionArray[i];
            switch(c) {
                case '(':
                    break;
                case ')':
                    operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    operators.push(toOperation(c));
                    break;
                default:
                    operands.push(Double.parseDouble(Character.toString(c)));
                    break;
            }
        }
        return operands.peek();
    }

    private static double incompletelyParentheticalEvaluation(char[] expressionArray) {
        Stack<Double> operands = new Stack<Double>();
        Stack<Operation> operators = new Stack<Operation>();

        for (int i = 0; i < expressionArray.length; i++) {
            char c = expressionArray[i];
            switch(c) {
                case '(':
                    operators.push(Operation.LeftParentheses);
                    break;
                case ')':
                    while (operators.peek() != Operation.LeftParentheses)
                        operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
                    operators.pop();
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    Operation op = toOperation(c);
                    while (!operators.isEmpty() && higherOrder(operators.peek(), op)) {
                        operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
                    }
                    operators.push(op);
                    break;
                default:
                    operands.push(Double.parseDouble(Character.toString(c)));
                    break;
            }
        }
        while (operands.size() != 1)
            operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
        return operands.peek();
    }

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

    private static boolean higherOrder(Operation operatorX, Operation operatorY) {
        return ((operatorX == Operation.Multiplication || operatorX == Operation.Division)
                && (operatorY == Operation.Addition || operatorY == Operation.Subtraction));
    }
}
