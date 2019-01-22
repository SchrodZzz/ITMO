package expressions;

public class Variable implements SuperExpression {
    private String var;

    public Variable(String var) {
        assert (var != null) : "Constructed with \"null\" argument";
        this.var = var;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (var) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                return 0;
        }
    }
}