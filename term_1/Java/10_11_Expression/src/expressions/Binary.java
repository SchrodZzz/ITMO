package expressions;

public abstract class Binary implements SuperExpression {
    private SuperExpression first;
    private SuperExpression second;

    Binary(SuperExpression first, SuperExpression second) {
        assert (first != null) && (second != null);
        this.first = first;
        this.second = second;
    }

    @Override
    public int evaluate(int x) {
        return apply(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return apply(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    protected abstract int apply(int first, int second);

    protected abstract double apply(double first, double second);
}