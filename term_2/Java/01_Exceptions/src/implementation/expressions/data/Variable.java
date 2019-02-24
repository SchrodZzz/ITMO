package implementation.expressions.data;

import kgeorgiy.expression.TripleExpression;

public class Variable implements TripleExpression {
    private String variableName;

    public Variable(String variableName) {
        assert variableName != null : "Name of variable is null";
        this.variableName = variableName;
    }

    public int evaluate(int x, int y, int z) {
        assert variableName.equals("x") || variableName.equals("y") || variableName.equals("z") : "Wrong variable name";
        switch (variableName) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
        }
        return 0;
    }
}
