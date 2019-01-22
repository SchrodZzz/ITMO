package expressions;

public class Subtract extends Binary {
    public Subtract(SuperExpression first, SuperExpression second) {
        super(first, second);
    }

    @Override
    protected int apply(int first, int second) {
        return first - second;
    }

    @Override
    protected double apply(double first, double second) {
        return first - second;
    }
}