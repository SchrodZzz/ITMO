package additionExpressions;

import expressions.TripleExpression;

public class BitwiseXor extends AbstractBinary {
    public BitwiseXor(TripleExpression firstValue, TripleExpression secondValue) {
        super(firstValue, secondValue);
    }

    protected int apply(int first, int second) {
        return first ^ second;
    }

}
